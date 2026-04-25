package com.skalipera.highfivequiz.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.skalipera.highfivequiz.data.local.entity.MatchHistoryEntity
import com.skalipera.highfivequiz.data.local.entity.RoundResultEntity

@Dao
interface MatchHistoryDao {

    @Upsert
    suspend fun upsertMatch(match: MatchHistoryEntity): Long

    @Upsert
    suspend fun upsertRoundResults(results: List<RoundResultEntity>)

    @Query("SELECT * FROM match_history WHERE sessionId = :sessionId LIMIT 1")
    suspend fun getBySessionId(sessionId: String): MatchHistoryEntity?
}
