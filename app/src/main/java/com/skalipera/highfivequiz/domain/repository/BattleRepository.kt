package com.skalipera.highfivequiz.domain.repository

import com.skalipera.highfivequiz.data.local.entity.RoundResultEntity
import com.skalipera.highfivequiz.domain.model.MatchSummary

interface BattleRepository {
    suspend fun saveMatch(summary: MatchSummary, roundResults: List<RoundResultEntity>)
}
