package com.skalipera.highfivequiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skalipera.highfivequiz.core.GameConstants

@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey val profileId: Long = 1L,
    val playerBaseHp: Int = GameConstants.PLAYER_BASE_HP,
    val rankPoints: Int = 1000,
    val gold: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val streak: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
