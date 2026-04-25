package com.skalipera.highfivequiz.data.repository

import com.skalipera.highfivequiz.data.local.dao.InventoryDao
import com.skalipera.highfivequiz.data.local.entity.InventoryEntity
import com.skalipera.highfivequiz.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow

class InventoryRepositoryImpl(
    private val inventoryDao: InventoryDao
) : InventoryRepository {

    override fun observeInventory(ownerProfileId: Long): Flow<List<InventoryEntity>> =
        inventoryDao.observeByOwner(ownerProfileId)

    override suspend fun upsert(item: InventoryEntity) {
        inventoryDao.upsert(item)
    }
}
