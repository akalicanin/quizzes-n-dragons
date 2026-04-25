package com.skalipera.highfivequiz.data.nearby

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.android.gms.nearby.connection.Strategy
import com.skalipera.highfivequiz.data.nfc.NfcBattleRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val TAG = "NearbyBattleManager"
private const val SERVICE_ID = "com.skalipera.highfivequiz.battle.nearby"

@Serializable
data class NearbyAttackPayload(
    val attackValue: Int,
    val sentAtMillis: Long = System.currentTimeMillis()
)

data class NearbyBattleState(
    val role: NfcBattleRole? = null,
    val localPlayerName: String = "Player",
    val connectedEndpointId: String? = null,
    val connectedPlayerName: String? = null,
    val status: String = "Nearby transport idle.",
    val lastError: String? = null,
    val lastReceivedAttack: NearbyAttackPayload? = null
)

object NearbyBattleManager {
    private val json = Json { ignoreUnknownKeys = true }
    private val strategy = Strategy.P2P_CLUSTER

    private val _state = MutableStateFlow(NearbyBattleState())
    val state: StateFlow<NearbyBattleState> = _state.asStateFlow()

    private var connectionsClient: ConnectionsClient? = null
    private var endpointName: String = "Player"
    private var localRole: NfcBattleRole? = null
    private val pendingEndpointNames: MutableMap<String, String> = mutableMapOf()

    fun startSession(context: Context, role: NfcBattleRole, playerName: String) {
        val appContext = context.applicationContext
        val client = Nearby.getConnectionsClient(appContext)
        connectionsClient = client
        endpointName = "${role.name.take(1)}-${playerName.ifBlank { "Player" }}"
        localRole = role
        _state.value = NearbyBattleState(
            role = role,
            localPlayerName = playerName.ifBlank { "Player" },
            status = if (role == NfcBattleRole.HOST) {
                "Nearby HOST advertising..."
            } else {
                "Nearby CLIENT discovering host..."
            }
        )
        client.stopAllEndpoints()

        if (role == NfcBattleRole.HOST) {
            client.startAdvertising(
                endpointName,
                SERVICE_ID,
                connectionLifecycleCallback,
                AdvertisingOptions.Builder().setStrategy(strategy).build()
            ).addOnFailureListener { e ->
                _state.value = _state.value.copy(lastError = e.message, status = "Nearby advertising failed.")
            }
        } else {
            client.startDiscovery(
                SERVICE_ID,
                endpointDiscoveryCallback,
                DiscoveryOptions.Builder().setStrategy(strategy).build()
            ).addOnFailureListener { e ->
                _state.value = _state.value.copy(lastError = e.message, status = "Nearby discovery failed.")
            }
        }
    }

    fun sendAttack(attackValue: Int) {
        val client = connectionsClient ?: return
        val endpointId = _state.value.connectedEndpointId ?: return
        val payloadBytes = json.encodeToString(
            NearbyAttackPayload.serializer(),
            NearbyAttackPayload(attackValue = attackValue)
        ).toByteArray()
        client.sendPayload(endpointId, Payload.fromBytes(payloadBytes))
            .addOnFailureListener { e ->
                _state.value = _state.value.copy(lastError = e.message, status = "Failed to send attack.")
            }
            .addOnSuccessListener {
                _state.value = _state.value.copy(status = "Attack sent over Nearby.")
            }
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            if (localRole != NfcBattleRole.CLIENT) return
            connectionsClient?.requestConnection(
                endpointName,
                endpointId,
                connectionLifecycleCallback
            )?.addOnFailureListener { e ->
                _state.value = _state.value.copy(lastError = e.message, status = "Connection request failed.")
            }
        }

        override fun onEndpointLost(endpointId: String) {
            if (_state.value.connectedEndpointId == endpointId) {
                _state.value = _state.value.copy(
                    connectedEndpointId = null,
                    connectedPlayerName = null,
                    status = "Nearby peer disconnected."
                )
            }
        }
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            pendingEndpointNames[endpointId] = connectionInfo.endpointName
            connectionsClient?.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                val peerName = pendingEndpointNames[endpointId]
                _state.value = _state.value.copy(
                    connectedEndpointId = endpointId,
                    connectedPlayerName = peerName,
                    status = if (peerName.isNullOrBlank()) {
                        "Nearby connected."
                    } else {
                        "Nearby connected to $peerName."
                    },
                    lastError = null
                )
                connectionsClient?.stopAdvertising()
                connectionsClient?.stopDiscovery()
            } else {
                _state.value = _state.value.copy(
                    status = "Nearby connection failed.",
                    lastError = result.status.statusMessage
                )
            }
            pendingEndpointNames.remove(endpointId)
        }

        override fun onDisconnected(endpointId: String) {
            _state.value = _state.value.copy(
                connectedEndpointId = null,
                connectedPlayerName = null,
                status = "Nearby disconnected."
            )
        }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            val bytes = payload.asBytes() ?: return
            val parsed = runCatching {
                json.decodeFromString(NearbyAttackPayload.serializer(), bytes.decodeToString())
            }.onFailure {
                Log.w(TAG, "Could not decode Nearby payload", it)
            }.getOrNull() ?: return

            _state.value = _state.value.copy(
                lastReceivedAttack = parsed,
                status = "Received attack ${parsed.attackValue} over Nearby."
            )
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) = Unit
    }
}
