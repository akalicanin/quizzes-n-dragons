package com.skalipera.highfivequiz.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.skalipera.highfivequiz.data.local.entity.BattleLoadoutEntity

@Dao
interface BattleLoadoutDao {

    @Upsert
    suspend fun upsert(loadout: BattleLoadoutEntity)

    @Query("SELECT * FROM battle_loadouts WHERE ownerProfileId = :ownerProfileId ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatestByOwner(ownerProfileId: Long): BattleLoadoutEntity?
}
