package com.skalipera.highfivequiz.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.skalipera.highfivequiz.data.local.entity.UserStatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserStatsDao {

    @Query("SELECT * FROM user_stats WHERE profileId = :profileId")
    fun observeByProfile(profileId: Long = 1L): Flow<UserStatsEntity?>

    @Query("SELECT * FROM user_stats WHERE profileId = :profileId")
    suspend fun getByProfile(profileId: Long = 1L): UserStatsEntity?

    @Upsert
    suspend fun upsert(entity: UserStatsEntity)

    @Query("SELECT COUNT(*) FROM user_stats")
    suspend fun countAll(): Int
}
