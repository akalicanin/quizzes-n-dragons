package com.skalipera.highfivequiz

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.skalipera.highfivequiz.data.nfc.HANDSHAKE_MIME_TYPE
import com.skalipera.highfivequiz.data.nfc.NfcHandshakeManager
import com.skalipera.highfivequiz.ui.navigation.HighFiveQuizApp
import com.skalipera.highfivequiz.ui.theme.HighFiveQuizTheme
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    private val nfcAdapter: NfcAdapter? by lazy { NfcAdapter.getDefaultAdapter(this) }
    private val nfcIntentFilters: Array<IntentFilter> by lazy {
        arrayOf(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                addDataType(HANDSHAKE_MIME_TYPE)
            },
            IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleNfcIntent(intent)
        setContent {
            HighFiveQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    HighFiveQuizApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val adapter = nfcAdapter ?: return
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        runCatching {
            adapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilters, null)
        }
        //updateOutgoingNfcMessage()
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleNfcIntent(intent)
    }

    //@RequiresPermission(Manifest.permission.WRITE_SECURE_SETTINGS)
//    fun updateOutgoingNfcMessage() {
//        val adapter = nfcAdapter ?: return
//        val payload = NfcHandshakeManager.createOutgoingPayload() ?: return
//        val dataBytes = payload.toByteArray(StandardCharsets.UTF_8)
//        val message = NdefMessage(
//            arrayOf(NdefRecord.createMime(HANDSHAKE_MIME_TYPE, dataBytes))
//        )
//        try {
//            adapter.setNdefPushMessage(message, this) // Again, ensure 'this' is an Activity!
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    private fun handleNfcIntent(intent: Intent?) {
        if (intent == null) return
        if (
            intent.action != NfcAdapter.ACTION_NDEF_DISCOVERED &&
            intent.action != NfcAdapter.ACTION_TAG_DISCOVERED &&
            intent.action != NfcAdapter.ACTION_TECH_DISCOVERED
        ) {
            return
        }
        val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES) ?: return
        rawMessages
            .mapNotNull { it as? NdefMessage }
            .flatMap { it.records.asList() }
            .forEach { record ->
                val mimeMatches = record.tnf == NdefRecord.TNF_MIME_MEDIA &&
                    record.type.contentEquals(HANDSHAKE_MIME_TYPE.toByteArray(StandardCharsets.US_ASCII))
                if (mimeMatches) {
                    val payload = String(record.payload, StandardCharsets.UTF_8)
                    NfcHandshakeManager.onIncomingPayload(payload)
                }
            }
    }
}
