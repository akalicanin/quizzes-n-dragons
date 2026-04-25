package com.skalipera.highfivequiz.data.nfc

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.UUID

const val HANDSHAKE_MIME_TYPE: String = "application/vnd.highfivequiz.handshake"

enum class NfcBattleRole {
    HOST,
    CLIENT
}

@Serializable
data class NfcHandshakePayload(
    val playerId: String,
    val playerName: String,
    val role: String,
    val createdAtMillis: Long
)

data class NfcHandshakeState(
    val role: NfcBattleRole? = null,
    val localPlayerId: String = UUID.randomUUID().toString(),
    val localPlayerName: String = "Player",
    val waitingForPeer: Boolean = false,
    val opponentPlayerName: String? = null,
    val status: String = "Choose HOST or CLIENT to start NFC battle.",
    val lastError: String? = null
)

object NfcHandshakeManager {
    private val json = Json { ignoreUnknownKeys = true }

    private val _state = MutableStateFlow(NfcHandshakeState())
    val state: StateFlow<NfcHandshakeState> = _state.asStateFlow()

    fun startSession(role: NfcBattleRole, playerName: String) {
        _state.value = _state.value.copy(
            role = role,
            localPlayerName = playerName.ifBlank { "Player" },
            waitingForPeer = true,
            opponentPlayerName = null,
            status = if (role == NfcBattleRole.HOST) {
                "HOST mode: waiting for CLIENT NFC tap."
            } else {
                "CLIENT mode: tap HOST phone to exchange data."
            },
            lastError = null
        )
    }

    fun createOutgoingPayload(): String? {
        val current = _state.value
        val role = current.role ?: return null
        val payload = NfcHandshakePayload(
            playerId = current.localPlayerId,
            playerName = current.localPlayerName,
            role = role.name,
            createdAtMillis = System.currentTimeMillis()
        )
        return json.encodeToString(NfcHandshakePayload.serializer(), payload)
    }

    fun onIncomingPayload(jsonPayload: String) {
        val parsed = runCatching {
            json.decodeFromString(NfcHandshakePayload.serializer(), jsonPayload)
        }.getOrElse {
            _state.value = _state.value.copy(
                lastError = "Invalid NFC payload",
                status = "NFC payload was received but could not be read."
            )
            return
        }

        val local = _state.value
        if (parsed.playerId == local.localPlayerId) return

        _state.value = local.copy(
            waitingForPeer = false,
            opponentPlayerName = parsed.playerName,
            status = "NFC connected with ${parsed.playerName}.",
            lastError = null
        )
    }
}
