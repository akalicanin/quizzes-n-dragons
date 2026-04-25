package com.skalipera.highfivequiz.data.nfc

import com.skalipera.highfivequiz.core.MatchPhase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NfcMatchCoordinator(
    private val validator: NfcPayloadValidator
) {

    private val consumedNonces: MutableSet<String> = linkedSetOf()

    private val _phase = MutableStateFlow(MatchPhase.SETUP)
    val phase: StateFlow<MatchPhase> = _phase.asStateFlow()

    private var sessionId: String? = null

    fun onTap1Received(payload: Tap1MatchSyncPayload): ValidationResult {
        val validation = validator.validateTap1(payload = payload, usedNonces = consumedNonces)
        if (validation is ValidationResult.Valid) {
            consumedNonces.add(payload.metadata.deviceNonce)
            sessionId = payload.sessionId
            _phase.value = MatchPhase.TRIVIA
        }
        return validation
    }

    fun onTap2Received(payload: Tap2ClashPayload): ValidationResult {
        val expectedSession = sessionId ?: return ValidationResult.Error("Session not initialized")
        val validation = validator.validateTap2(
            payload = payload,
            expectedSessionId = expectedSession,
            usedNonces = consumedNonces
        )
        if (validation is ValidationResult.Valid) {
            consumedNonces.add(payload.metadata.deviceNonce)
            _phase.value = MatchPhase.ROUND_RESOLVE
        }
        return validation
    }

    fun moveToWaitingForOpponent() {
        _phase.value = MatchPhase.WAITING
    }

    fun moveToDragonSelection() {
        _phase.value = MatchPhase.DRAGON_SELECT
    }

    fun moveToClashPhase() {
        _phase.value = MatchPhase.TAP_2_CLASH
    }

    fun reset() {
        consumedNonces.clear()
        sessionId = null
        _phase.value = MatchPhase.SETUP
    }
}
