package com.skalipera.highfivequiz.data.repository

import com.skalipera.highfivequiz.data.local.dao.BattleLoadoutDao
import com.skalipera.highfivequiz.data.local.dao.DragonDao
import com.skalipera.highfivequiz.data.local.entity.BattleLoadoutEntity
import com.skalipera.highfivequiz.data.local.entity.DragonEntity
import com.skalipera.highfivequiz.domain.repository.DragonRepository
import kotlinx.coroutines.flow.Flow

class DragonRepositoryImpl(
    private val dragonDao: DragonDao,
    private val battleLoadoutDao: BattleLoadoutDao
) : DragonRepository {

    override fun observeDragons(ownerProfileId: Long): Flow<List<DragonEntity>> = dragonDao.observeByOwner(ownerProfileId)

    override suspend fun getDragonsByIds(ownerProfileId: Long, ids: List<Long>): List<DragonEntity> =
        dragonDao.getByIds(ownerProfileId, ids)

    override suspend fun saveLoadout(loadout: BattleLoadoutEntity) {
        battleLoadoutDao.upsert(loadout)
    }

    override suspend fun getLatestLoadout(ownerProfileId: Long): BattleLoadoutEntity? =
        battleLoadoutDao.getLatestByOwner(ownerProfileId)
}
