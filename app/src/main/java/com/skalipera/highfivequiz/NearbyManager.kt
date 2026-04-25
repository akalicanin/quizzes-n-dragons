package com.skalipera.highfivequiz

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.skalipera.highfivequiz.viewmodel.GameViewModel


class NearbyController(
    private val context: Context,
    private val viewModel: GameViewModel // Dodajemo referencu na ViewModel
) {
    private val connectionsClient = Nearby.getConnectionsClient(context)
    private val serviceId = "com.skalipera.highfivequiz.P2P"
    var opponentEndpointId: String? = null

    // --- CALLBACKS ---
    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            connectionsClient.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                opponentEndpointId = endpointId
                // Javljamo ViewModel-u da smo uspeli!
                viewModel.onConnectionSuccess(endpointId)
            } else {
                viewModel.onSearchingStatusChanged(false)
            }
        }

        override fun onDisconnected(endpointId: String) {
            opponentEndpointId = null
            // Ovde možeš dodati logiku za prekid veze
        }
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            connectionsClient.requestConnection(viewModel.playerNickname, endpointId, connectionLifecycleCallback)
        }
        override fun onEndpointLost(endpointId: String) {}
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            // Logika za primanje pitanja/odgovora
        }
        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {}
    }

    // --- FUNKCIJE ---
    fun startPlay() {
        viewModel.onSearchingStatusChanged(true)
        val advOptions = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        val discOptions = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build()

        connectionsClient.startAdvertising(viewModel.playerNickname, serviceId, connectionLifecycleCallback, advOptions)
        connectionsClient.startDiscovery(serviceId, endpointDiscoveryCallback, discOptions)
    }
}