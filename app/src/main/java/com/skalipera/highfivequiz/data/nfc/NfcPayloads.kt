package com.skalipera.highfivequiz.data.nfc

import com.skalipera.highfivequiz.core.DragonType
import com.skalipera.highfivequiz.core.MatchRole
import com.skalipera.highfivequiz.core.QuestionCategory
import kotlinx.serialization.Serializable

@Serializable
data class SharedMetadataPayload(
    val protocolVersion: Int,
    val appVersion: String,
    val deviceNonce: String,
    val role: MatchRole,
    val createdAtMillis: Long
)

@Serializable
data class Tap1MatchSyncPayload(
    val sessionId: String,
    val protocolVersion: Int,
    val hostSeed: Long,
    val bannedCategories: List<QuestionCategory>,
    val questionIds: List<Long>,
    val createdAtMillis: Long,
    val checksum: String? = null,
    val metadata: SharedMetadataPayload
)

@Serializable
data class QuestionAttackSummaryPayload(
    val questionId: Long,
    val isCorrect: Boolean,
    val wasSkipped: Boolean,
    val attackAwarded: Int
)

@Serializable
data class Tap2ClashPayload(
    val sessionId: String,
    val selectedDragonId: Long,
    val selectedDragonType: DragonType,
    val totalAttack: Int,
    val questionSummary: List<QuestionAttackSummaryPayload>,
    val createdAtMillis: Long,
    val checksum: String? = null,
    val metadata: SharedMetadataPayload
)
