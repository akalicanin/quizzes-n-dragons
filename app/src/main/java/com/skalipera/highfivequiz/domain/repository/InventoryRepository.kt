package com.skalipera.highfivequiz.domain.repository

import com.skalipera.highfivequiz.data.local.entity.InventoryEntity
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    fun observeInventory(ownerProfileId: Long): Flow<List<InventoryEntity>>
    suspend fun upsert(item: InventoryEntity)
}
