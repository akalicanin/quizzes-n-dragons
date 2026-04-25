package com.skalipera.highfivequiz.domain.repository

import com.skalipera.highfivequiz.data.local.entity.BattleLoadoutEntity
import com.skalipera.highfivequiz.data.local.entity.DragonEntity
import kotlinx.coroutines.flow.Flow

interface DragonRepository {
    fun observeDragons(ownerProfileId: Long): Flow<List<DragonEntity>>
    suspend fun getDragonsByIds(ownerProfileId: Long, ids: List<Long>): List<DragonEntity>
    suspend fun saveLoadout(loadout: BattleLoadoutEntity)
    suspend fun getLatestLoadout(ownerProfileId: Long): BattleLoadoutEntity?
}
