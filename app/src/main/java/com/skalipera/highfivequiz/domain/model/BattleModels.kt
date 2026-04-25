package com.skalipera.highfivequiz.domain.model

import com.skalipera.highfivequiz.core.DragonType
import com.skalipera.highfivequiz.core.MatchOutcome
import com.skalipera.highfivequiz.core.QuestionCategory

data class AnswerOutcome(
    val questionId: Long,
    val category: QuestionCategory,
    val isCorrect: Boolean,
    val isSkipped: Boolean
)

data class ClashResolution(
    val netDamageToDefender: Int,
    val defenderDragonHpAfter: Int,
    val defenderPlayerHpAfter: Int,
    val attackerWonClash: Boolean
)

data class MatchRewards(
    val rankPointsDelta: Int,
    val goldDelta: Int
)

data class MatchSummary(
    val sessionId: String,
    val selectedDragonId: Long,
    val selectedDragonType: DragonType,
    val attackScore: Int,
    val opponentAttackScore: Int,
    val netDamageDealt: Int,
    val netDamageTaken: Int,
    val outcome: MatchOutcome,
    val reward: MatchRewards
)
