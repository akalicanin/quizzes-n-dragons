package com.skalipera.highfivequiz

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*

class NearbyController(private val context: Context) {
    private val connectionsClient = Nearby.getConnectionsClient(context)
    private val serviceId = "com.skalipera.highfivequiz.P2P"

    var opponentEndpointId: String? = null
    var isSearching = false

    // 1. Kad klikneš "PLAY", radiš obe stvari
    fun startSearching(playerName: String) {
        if (isSearching) return // Da ne pokrećeš dvaput
        isSearching = true

        val advOptions = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        val discOptions = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build()

        // Oglašavaj se (budi vidljiv)
        connectionsClient.startAdvertising(playerName, serviceId, connectionLifecycleCallback, advOptions)
            .addOnFailureListener { Log.e("Nearby", "Advertising failed: ${it.message}") }

        // Traži druge (gledaj ko je vidljiv)
        connectionsClient.startDiscovery(serviceId, endpointDiscoveryCallback, discOptions)
            .addOnFailureListener { Log.e("Nearby", "Discovery failed: ${it.message}") }
    }

    // 2. CALLBACK: Šta se desi kad nađemo nekoga
    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            Log.d("Nearby", "Pronađen igrač: ${info.endpointName}. Šaljem zahtev...")
            // Čim nađeš nekoga, pošalji zahtev.
            // Nearby će sam rešiti ako oba telefona pošalju zahtev istovremeno.
            connectionsClient.requestConnection("Igrac", endpointId, connectionLifecycleCallback)
        }

        override fun onEndpointLost(endpointId: String) {}
    }

    // 3. CALLBACK: Životni ciklus veze
    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            Log.d("Nearby", "Veza inicirana sa ${info.endpointName}. Prihvatam...")
            connectionsClient.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                Log.d("Nearby", "POVEZANI!")
                opponentEndpointId = endpointId
                isSearching = false

                // KLJUČNO: Prestani da tražiš jer si našao par
                connectionsClient.stopAdvertising()
                connectionsClient.stopDiscovery()
            }
        }

        override fun onDisconnected(endpointId: String) {
            opponentEndpointId = null
            Log.d("Nearby", "Veza prekinuta.")
        }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            if (payload.type == Payload.Type.BYTES) {
                val poruka = String(payload.asBytes()!!)
                Log.d("Nearby", "Stigla poruka: $poruka")
            }
        }
        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {}
    }

    fun sendMsg(text: String) {
        opponentEndpointId?.let {
            connectionsClient.sendPayload(it, Payload.fromBytes(text.toByteArray()))
        }
    }
}