package com.skalipera.highfivequiz.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.skalipera.highfivequiz.data.local.entity.DragonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DragonDao {

    @Query("SELECT * FROM dragons WHERE ownerProfileId = :ownerProfileId ORDER BY id ASC")
    fun observeByOwner(ownerProfileId: Long): Flow<List<DragonEntity>>

    @Query("SELECT * FROM dragons WHERE ownerProfileId = :ownerProfileId AND id IN (:ids)")
    suspend fun getByIds(ownerProfileId: Long, ids: List<Long>): List<DragonEntity>

    @Query("SELECT * FROM dragons WHERE id = :id")
    suspend fun getById(id: Long): DragonEntity?

    @Upsert
    suspend fun upsertAll(entities: List<DragonEntity>)

    @Upsert
    suspend fun upsert(entity: DragonEntity)

    @Query("SELECT COUNT(*) FROM dragons")
    suspend fun countAll(): Int
}
