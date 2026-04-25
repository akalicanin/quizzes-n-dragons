package com.skalipera.highfivequiz.data.repository

import androidx.room.withTransaction
import com.skalipera.highfivequiz.data.local.database.AppDatabase
import com.skalipera.highfivequiz.data.local.entity.MatchHistoryEntity
import com.skalipera.highfivequiz.data.local.entity.RoundResultEntity
import com.skalipera.highfivequiz.domain.model.MatchSummary
import com.skalipera.highfivequiz.domain.repository.BattleRepository

class BattleRepositoryImpl(
    private val database: AppDatabase
) : BattleRepository {

    override suspend fun saveMatch(summary: MatchSummary, roundResults: List<RoundResultEntity>) {
        database.withTransaction {
            val insertedId = database.matchHistoryDao().upsertMatch(
                MatchHistoryEntity(
                    ownerProfileId = 1L,
                    sessionId = summary.sessionId,
                    wasHost = true,
                    attackScore = summary.attackScore,
                    opponentAttackScore = summary.opponentAttackScore,
                    selectedDragonId = summary.selectedDragonId,
                    selectedDragonType = summary.selectedDragonType,
                    netDamageDealt = summary.netDamageDealt,
                    netDamageTaken = summary.netDamageTaken,
                    outcome = summary.outcome,
                    rewardRankPoints = summary.reward.rankPointsDelta,
                    rewardGold = summary.reward.goldDelta
                )
            )
            val hydratedRoundResults = roundResults.map { it.copy(matchHistoryId = insertedId) }
            database.matchHistoryDao().upsertRoundResults(hydratedRoundResults)
        }
    }
}
