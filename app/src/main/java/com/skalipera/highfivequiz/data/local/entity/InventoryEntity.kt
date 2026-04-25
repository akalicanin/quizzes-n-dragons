package com.skalipera.highfivequiz.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.skalipera.highfivequiz.core.InventoryItemType

@Entity(
    tableName = "inventory",
    indices = [
        Index(value = ["ownerProfileId", "itemType", "itemId"], unique = true),
        Index(value = ["ownerProfileId"])
    ]
)
data class InventoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val ownerProfileId: Long,
    val itemType: InventoryItemType,
    val itemId: String,
    val quantity: Int,
    val isEquipped: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
