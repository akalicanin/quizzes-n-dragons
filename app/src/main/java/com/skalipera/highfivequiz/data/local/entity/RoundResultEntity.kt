package com.skalipera.highfivequiz.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.skalipera.highfivequiz.core.QuestionCategory

@Entity(
    tableName = "round_results",
    indices = [
        Index(value = ["matchHistoryId"]),
        Index(value = ["questionId"])
    ]
)
data class RoundResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val matchHistoryId: Long,
    val questionId: Long,
    val category: QuestionCategory,
    val wasCorrect: Boolean,
    val wasSkipped: Boolean,
    val baseAttack: Int,
    val amplifiedAttack: Int
)
