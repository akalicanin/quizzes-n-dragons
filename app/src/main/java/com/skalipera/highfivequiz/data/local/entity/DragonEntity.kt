package com.skalipera.highfivequiz.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.skalipera.highfivequiz.core.CardType
import com.skalipera.highfivequiz.core.DragonType
import com.skalipera.highfivequiz.core.GameConstants

@Entity(
    tableName = "dragons",
    indices = [
        Index(value = ["ownerProfileId", "type"]),
        Index(value = ["ownerProfileId"])
    ]
)
data class DragonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val ownerProfileId: Long = 1L,
    val name: String,
    val type: DragonType,
    val level: Int = 1,
    val experience: Int = 0,
    val currentHp: Int = GameConstants.DRAGON_BASE_HP,
    val maxHp: Int = GameConstants.DRAGON_BASE_HP,
    val equippedCostumeId: String? = null,
    val equippedCard: CardType? = null,
    val isActive: Boolean = true,
    val feedLevel: Int = 0,
    val petLevel: Int = 0,
    val updatedAt: Long = System.currentTimeMillis()
)
