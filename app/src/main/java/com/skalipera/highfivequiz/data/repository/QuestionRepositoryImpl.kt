package com.skalipera.highfivequiz.data.repository

import com.skalipera.highfivequiz.core.QuestionCategory
import com.skalipera.highfivequiz.data.local.dao.QuestionDao
import com.skalipera.highfivequiz.data.local.entity.QuestionEntity
import com.skalipera.highfivequiz.domain.repository.QuestionRepository

class QuestionRepositoryImpl(
    private val questionDao: QuestionDao
) : QuestionRepository {

    override suspend fun getRandomByCategory(category: QuestionCategory, limit: Int): List<QuestionEntity> =
        questionDao.getRandomByCategory(category, limit)

    override suspend fun getRandomExcludingCategory(category: QuestionCategory?, limit: Int): List<QuestionEntity> =
        questionDao.getRandomExcludingCategory(category, limit)

    override suspend fun getByIds(ids: List<Long>): List<QuestionEntity> = questionDao.getByIds(ids)
}
