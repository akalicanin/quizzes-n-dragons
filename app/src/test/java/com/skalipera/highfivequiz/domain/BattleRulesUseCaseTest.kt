package com.skalipera.highfivequiz.domain

import com.skalipera.highfivequiz.core.DragonType
import com.skalipera.highfivequiz.core.MatchOutcome
import com.skalipera.highfivequiz.core.QuestionCategory
import com.skalipera.highfivequiz.domain.model.AnswerOutcome
import com.skalipera.highfivequiz.domain.usecase.ApplyClashDamageUseCase
import com.skalipera.highfivequiz.domain.usecase.ComputeRoundAttackUseCase
import com.skalipera.highfivequiz.domain.usecase.EndMatchRewardsUseCase
import com.skalipera.highfivequiz.domain.usecase.ShouldEndMatchUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BattleRulesUseCaseTest {

    @Test
    fun `compute attack applies multiplier for matching category and dragon type`() {
        val useCase = ComputeRoundAttackUseCase()
        val answers = listOf(
            AnswerOutcome(1, QuestionCategory.BIO, isCorrect = true, isSkipped = false),
            AnswerOutcome(2, QuestionCategory.HISTORY, isCorrect = true, isSkipped = false),
            AnswerOutcome(3, QuestionCategory.BIO, isCorrect = false, isSkipped = false)
        )

        val attack = useCase(DragonType.BIO, answers)

        assertEquals(21, attack)
    }

    @Test
    fun `clash damage overflows from dragon hp to player hp`() {
        val useCase = ApplyClashDamageUseCase()

        val result = useCase(
            attackerScore = 80,
            defenderScore = 50,
            defenderDragonHp = 20,
            defenderPlayerHp = 150
        )

        assertEquals(30, result.netDamageToDefender)
        assertEquals(0, result.defenderDragonHpAfter)
        assertEquals(140, result.defenderPlayerHpAfter)
        assertTrue(result.attackerWonClash)
    }

    @Test
    fun `match ends when player hp is zero or no dragons alive`() {
        val useCase = ShouldEndMatchUseCase()

        assertTrue(useCase(playerHp = 0, aliveDragonCount = 2))
        assertTrue(useCase(playerHp = 10, aliveDragonCount = 0))
        assertFalse(useCase(playerHp = 10, aliveDragonCount = 1))
    }

    @Test
    fun `reward ranges are deterministic with seed and respect outcome`() {
        val useCase = EndMatchRewardsUseCase()

        val win = useCase(outcome = MatchOutcome.WIN, streak = 3, seed = 1L)
        val loss = useCase(outcome = MatchOutcome.LOSS, streak = 0, seed = 1L)

        assertTrue(win.rankPointsDelta > 0)
        assertTrue(win.goldDelta > 0)
        assertTrue(loss.rankPointsDelta <= 0)
        assertTrue(loss.goldDelta > 0)
    }
}
