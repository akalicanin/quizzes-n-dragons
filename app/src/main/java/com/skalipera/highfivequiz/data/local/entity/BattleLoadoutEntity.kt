package com.skalipera.highfivequiz.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.skalipera.highfivequiz.core.QuestionCategory

@Entity(
    tableName = "battle_loadouts",
    indices = [
        Index(value = ["ownerProfileId"]),
        Index(value = ["createdAt"])
    ]
)
data class BattleLoadoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val ownerProfileId: Long,
    val selectedDragonIds: List<Long>,
    val bannedCategory: QuestionCategory?,
    val createdAt: Long = System.currentTimeMillis()
)
