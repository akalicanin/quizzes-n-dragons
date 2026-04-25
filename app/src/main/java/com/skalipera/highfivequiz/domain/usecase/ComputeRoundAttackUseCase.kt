package com.skalipera.highfivequiz.domain.usecase

import com.skalipera.highfivequiz.core.DragonType
import com.skalipera.highfivequiz.core.GameConstants
import com.skalipera.highfivequiz.domain.model.AnswerOutcome
import kotlin.math.roundToInt

class ComputeRoundAttackUseCase {

    operator fun invoke(selectedDragonType: DragonType, answers: List<AnswerOutcome>): Int {
        return answers.sumOf { answer ->
            if (!answer.isCorrect || answer.isSkipped) {
                0
            } else {
                val isAmplified = selectedDragonType.name == answer.category.name
                if (isAmplified) {
                    (GameConstants.CORRECT_ANSWER_BASE_ATTACK * GameConstants.ATTACK_MATCH_MULTIPLIER).roundToInt()
                } else {
                    GameConstants.CORRECT_ANSWER_BASE_ATTACK
                }
            }
        }
    }
}
