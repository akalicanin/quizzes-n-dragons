package com.skalipera.highfivequiz.data.repository

import com.skalipera.highfivequiz.data.local.dao.UserStatsDao
import com.skalipera.highfivequiz.data.local.entity.UserStatsEntity
import com.skalipera.highfivequiz.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userStatsDao: UserStatsDao
) : UserRepository {

    override fun observeUserStats(profileId: Long): Flow<UserStatsEntity?> = userStatsDao.observeByProfile(profileId)

    override suspend fun getUserStats(profileId: Long): UserStatsEntity? = userStatsDao.getByProfile(profileId)

    override suspend fun upsertUserStats(stats: UserStatsEntity) {
        userStatsDao.upsert(stats)
    }
}
