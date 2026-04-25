package com.skalipera.highfivequiz.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.skalipera.highfivequiz.core.DragonType
import com.skalipera.highfivequiz.core.MatchOutcome

@Entity(
    tableName = "match_history",
    indices = [
        Index(value = ["ownerProfileId"]),
        Index(value = ["sessionId"], unique = true)
    ]
)
data class MatchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val ownerProfileId: Long,
    val sessionId: String,
    val wasHost: Boolean,
    val attackScore: Int,
    val opponentAttackScore: Int,
    val selectedDragonId: Long,
    val selectedDragonType: DragonType,
    val netDamageDealt: Int,
    val netDamageTaken: Int,
    val outcome: MatchOutcome,
    val rewardRankPoints: Int,
    val rewardGold: Int,
    val createdAt: Long = System.currentTimeMillis()
)
