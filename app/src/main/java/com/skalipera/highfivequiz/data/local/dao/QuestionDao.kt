package com.skalipera.highfivequiz.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.skalipera.highfivequiz.core.QuestionCategory
import com.skalipera.highfivequiz.data.local.entity.QuestionEntity

@Dao
interface QuestionDao {

    @Upsert
    suspend fun upsertAll(questions: List<QuestionEntity>)

    @Query("SELECT * FROM questions WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<Long>): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE isActive = 1 AND category = :category ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomByCategory(category: QuestionCategory, limit: Int): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE isActive = 1 AND (:excludedCategory IS NULL OR category != :excludedCategory) ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomExcludingCategory(excludedCategory: QuestionCategory?, limit: Int): List<QuestionEntity>

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun countAll(): Int
}
