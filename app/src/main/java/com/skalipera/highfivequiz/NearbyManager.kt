package com.skalipera.highfivequiz

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.skalipera.highfivequiz.viewmodel.GameViewModel


class NearbyController(
    private val context: Context,
    private val viewModel: GameViewModel
) {
    private val connectionsClient = Nearby.getConnectionsClient(context)
    private val serviceId = "com.skalipera.highfivequiz.P2P"
    var opponentEndpointId: String? = null
    var opponentName: String? = null
    private var isConnecting = false

    // --- CALLBACKS ---
    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            Log.d("NearbyController", "Connection initiated with $endpointId (${info.endpointName})")
            isConnecting = true
            
            // Extract original nickname
            opponentName = info.endpointName.substringBefore("|")
            
            // Stop everything to stabilize this connection
            connectionsClient.stopAdvertising()
            connectionsClient.stopDiscovery()

            connectionsClient.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            isConnecting = false
            if (result.status.isSuccess) {
                Log.d("NearbyController", "Connection successful!")
                opponentEndpointId = endpointId

                val remoteId = opponentName?.substringAfter("|", "0") ?: "0"
                val isHost = viewModel.myNearbyId < remoteId

                viewModel.onConnectionSuccess(opponentName?.substringBefore("|") ?: endpointId, isHost)

            } else {
                Log.e("NearbyController", "Connection failed: ${result.status.statusMessage}")
                // Clear state so we can try again
                opponentEndpointId = null
                // Don't restart immediately to avoid tight loops/crashes
            }
        }

        override fun onDisconnected(endpointId: String) {
            opponentEndpointId = null
            isConnecting = false
            Log.d("NearbyController", "Disconnected from $endpointId")
            viewModel.onOpponentDisconnected()
        }
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            Log.d("NearbyController", "Found endpoint: $endpointId (${info.endpointName})")
            
            if (isConnecting) return

            // TIE-BREAKER LOGIC:
            // Both devices are advertising and discovering. We only want ONE to call requestConnection.
            // We compare our unique IDs. The one with the smaller ID initiates.
            val myId = viewModel.myNearbyId
            val remoteId = info.endpointName.substringAfter("|", "0")

            if (myId < remoteId) {
                Log.d("NearbyController", "Tie-breaker: I am the initiator. Requesting...")
                isConnecting = true
                connectionsClient.stopDiscovery()
                connectionsClient.stopAdvertising()
                
                connectionsClient.requestConnection(
                    "${viewModel.playerNickname}|${viewModel.myNearbyId}",
                    endpointId,
                    connectionLifecycleCallback
                )
            } else {
                Log.d("NearbyController", "Tie-breaker: I am the waiter. Waiting for them to request...")
            }
        }
        override fun onEndpointLost(endpointId: String) {}
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload)
        {

        }
        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {}
    }

    fun sendData(jsonPayload: String) {
        opponentEndpointId?.let { endpoint ->
            connectionsClient.sendPayload(endpoint, Payload.fromBytes(jsonPayload.toByteArray()))
        }
    }
    // --- FUNKCIJE ---
    fun startPlay() {
        if (isConnecting) return
        
        viewModel.onSearchingStatusChanged(true)
        
        // Use P2P_CLUSTER for symmetric one-button discovery
        val advOptions = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build()
        val discOptions = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build()

        val advertisingName = "${viewModel.playerNickname}|${viewModel.myNearbyId}"
        
        connectionsClient.startAdvertising(advertisingName, serviceId, connectionLifecycleCallback, advOptions)
            .addOnFailureListener { Log.e("NearbyController", "Advertising failed: ${it.message}") }
            
        connectionsClient.startDiscovery(serviceId, endpointDiscoveryCallback, discOptions)
            .addOnFailureListener { Log.e("NearbyController", "Discovery failed: ${it.message}") }
    }
    
    fun stopAll() {
        isConnecting = false
        connectionsClient.stopAdvertising()
        connectionsClient.stopDiscovery()
        connectionsClient.stopAllEndpoints()
    }
}