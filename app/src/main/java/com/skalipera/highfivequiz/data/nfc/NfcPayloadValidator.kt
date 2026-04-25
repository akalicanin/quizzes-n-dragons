package com.skalipera.highfivequiz.data.nfc

import com.skalipera.highfivequiz.core.GameConstants

class NfcPayloadValidator {

    fun validateTap1(
        payload: Tap1MatchSyncPayload,
        usedNonces: Set<String>,
        expectedQuestionCount: Int = GameConstants.QUESTIONS_PER_MATCH,
        nowMillis: Long = System.currentTimeMillis()
    ): ValidationResult {
        if (payload.createdAtMillis + GameConstants.SESSION_STALE_AFTER_MILLIS < nowMillis) {
            return ValidationResult.Error("Stale session")
        }
        if (payload.metadata.deviceNonce in usedNonces) {
            return ValidationResult.Error("Replay detected")
        }
        if (payload.questionIds.size != expectedQuestionCount) {
            return ValidationResult.Error("Invalid question set size")
        }
        if (payload.questionIds.distinct().size != payload.questionIds.size) {
            return ValidationResult.Error("Duplicate question IDs")
        }
        return ValidationResult.Valid
    }

    fun validateTap2(
        payload: Tap2ClashPayload,
        expectedSessionId: String,
        usedNonces: Set<String>,
        nowMillis: Long = System.currentTimeMillis()
    ): ValidationResult {
        if (payload.sessionId != expectedSessionId) {
            return ValidationResult.Error("Session mismatch")
        }
        if (payload.createdAtMillis + GameConstants.SESSION_STALE_AFTER_MILLIS < nowMillis) {
            return ValidationResult.Error("Stale clash payload")
        }
        if (payload.metadata.deviceNonce in usedNonces) {
            return ValidationResult.Error("Replay detected")
        }
        return ValidationResult.Valid
    }
}

sealed interface ValidationResult {
    data object Valid : ValidationResult
    data class Error(val message: String) : ValidationResult
}
