package com.skalipera.highfivequiz.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.skalipera.highfivequiz.core.QuestionCategory

@Entity(
    tableName = "questions",
    indices = [
        Index(value = ["category"]),
        Index(value = ["isActive"])
    ]
)
data class QuestionEntity(
    @PrimaryKey val id: Long,
    val questionText: String,
    val category: QuestionCategory,
    val options: List<String>,
    val correctOptionIndex: Int,
    val difficulty: Int = 1,
    val tags: List<String> = emptyList(),
    val isActive: Boolean = true
)
