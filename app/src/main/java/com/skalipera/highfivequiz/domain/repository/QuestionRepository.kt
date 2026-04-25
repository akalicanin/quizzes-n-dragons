package com.skalipera.highfivequiz.domain.repository

import com.skalipera.highfivequiz.core.QuestionCategory
import com.skalipera.highfivequiz.data.local.entity.QuestionEntity

interface QuestionRepository {
    suspend fun getRandomByCategory(category: QuestionCategory, limit: Int): List<QuestionEntity>
    suspend fun getRandomExcludingCategory(category: QuestionCategory?, limit: Int): List<QuestionEntity>
    suspend fun getByIds(ids: List<Long>): List<QuestionEntity>
}
