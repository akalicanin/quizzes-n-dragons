package com.skalipera.highfivequiz.domain.usecase

import com.skalipera.highfivequiz.core.GameConstants
import com.skalipera.highfivequiz.core.QuestionCategory
import com.skalipera.highfivequiz.domain.repository.QuestionRepository
import kotlin.random.Random

class GenerateHostQuestionSetUseCase(
    private val questionRepository: QuestionRepository
) {

    suspend operator fun invoke(
        bannedCategory: QuestionCategory?,
        seed: Long
    ): List<Long> {
        val categories = QuestionCategory.entries.filter { it != bannedCategory }
        val random = Random(seed)
        val selectedCategories = categories.shuffled(random).take(2)

        val firstBatch = questionRepository.getRandomByCategory(
            category = selectedCategories[0],
            limit = GameConstants.QUESTIONS_PER_CATEGORY
        )
        val secondBatch = questionRepository.getRandomByCategory(
            category = selectedCategories[1],
            limit = GameConstants.QUESTIONS_PER_CATEGORY
        )

        return (firstBatch + secondBatch)
            .map { it.id }
            .shuffled(random)
            .take(GameConstants.QUESTIONS_PER_MATCH)
    }
}
