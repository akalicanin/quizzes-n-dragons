package com.skalipera.highfivequiz.domain.repository

import com.skalipera.highfivequiz.data.local.entity.UserStatsEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUserStats(profileId: Long = 1L): Flow<UserStatsEntity?>
    suspend fun getUserStats(profileId: Long = 1L): UserStatsEntity?
    suspend fun upsertUserStats(stats: UserStatsEntity)
}
