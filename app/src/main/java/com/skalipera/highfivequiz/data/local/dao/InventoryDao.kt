package com.skalipera.highfivequiz.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.skalipera.highfivequiz.data.local.entity.InventoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query("SELECT * FROM inventory WHERE ownerProfileId = :ownerProfileId ORDER BY itemType, itemId")
    fun observeByOwner(ownerProfileId: Long): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM inventory WHERE ownerProfileId = :ownerProfileId AND itemType = :itemType AND itemId = :itemId LIMIT 1")
    suspend fun getByItem(ownerProfileId: Long, itemType: String, itemId: String): InventoryEntity?

    @Upsert
    suspend fun upsert(entity: InventoryEntity)
}
