package com.skalipera.highfivequiz

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skalipera.highfivequiz.ui.utility.GamePayload
import com.skalipera.highfivequiz.ui.utility.PayloadType
import com.skalipera.highfivequiz.viewmodel.GameViewModel

class NearbyController(
    private val context: Context,
    private val viewModel: GameViewModel
) {
    private val connectionsClient = Nearby.getConnectionsClient(context)
    private val serviceId = "com.skalipera.highfivequiz.P2P"

    private val gson = Gson()
    var opponentEndpointId: String? = null
    var opponentName: String? = null
    private var remoteId: String = "0"
    private var isConnecting = false

    // --- CALLBACKS ---
    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            Log.d("NearbyController", "Connection initiated with $endpointId (${info.endpointName})")
            isConnecting = true
            
            // Extract original nickname
            //opponentName = info.endpointName.substringBefore("|")

            val parts = info.endpointName.split("|")
            opponentName = parts.getOrNull(0) ?: info.endpointName
            remoteId = parts.getOrNull(1) ?: "0" // <--- SAVE THE ID HERE

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

                //val remoteId = opponentName?.substringAfter("|", "0") ?: "0"
                val isHost = viewModel.myNearbyId < remoteId
                Log.d("NearbyController", "Am I host? $isHost (MyID: ${viewModel.myNearbyId}, RemoteID: $remoteId)")

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
            if (payload.type == Payload.Type.BYTES) {
                val jsonString = String(payload.asBytes()!!)

                //Convert the incoming string back into our GamePayload wrapper
                val gamePayload = gson.fromJson(jsonString, GamePayload::class.java)

                // Decide what to do based on the type
                when (gamePayload.type) {
                    PayloadType.READY -> {
                        viewModel.onOpponentReadyReceived()
                    }
                    PayloadType.DRAGON_ID -> {
                        // The data is just the simple string ID
                        viewModel.onOpponentDragonReceived(gamePayload.data)
                    }
                    PayloadType.QUESTIONS -> {
                        // Because it's a List<Question>, Gson needs a TypeToken to parse it correctly
                        val listType = object : TypeToken<List<GameViewModel.Question>>() {}.type
                        val questions: List<GameViewModel.Question> = gson.fromJson(gamePayload.data, listType)

                        viewModel.onQuestionsReceivedFromHost(questions)
                    }
                    PayloadType.ROUND_SCORE -> {
                        // The data is just a number string
                        val score = gamePayload.data.toInt()
                        viewModel.onOpponentFinishedRound(score)
                    }
                    PayloadType.GAME_OVER -> {
                        viewModel.onGameOverReceived()
                    }
                    PayloadType.REMATCH -> {
                        viewModel.onOpponentRematchReceived()
                    }
                }
            }
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