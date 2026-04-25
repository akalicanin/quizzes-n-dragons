package com.skalipera.highfivequiz.domain.usecase

import com.skalipera.highfivequiz.core.GameConstants
import com.skalipera.highfivequiz.core.MatchOutcome
import com.skalipera.highfivequiz.domain.model.MatchRewards
import kotlin.random.Random

class EndMatchRewardsUseCase {

    operator fun invoke(outcome: MatchOutcome, streak: Int, seed: Long = System.currentTimeMillis()): MatchRewards {
        val random = Random(seed)
        return when (outcome) {
            MatchOutcome.WIN -> MatchRewards(
                rankPointsDelta = random.nextInt(
                    GameConstants.WIN_RANK_REWARD_RANGE.first,
                    GameConstants.WIN_RANK_REWARD_RANGE.last + 1
                ) + streak,
                goldDelta = random.nextInt(
                    GameConstants.WIN_GOLD_REWARD_RANGE.first,
                    GameConstants.WIN_GOLD_REWARD_RANGE.last + 1
                )
            )

            MatchOutcome.LOSS,
            MatchOutcome.SURRENDER -> MatchRewards(
                rankPointsDelta = random.nextInt(
                    GameConstants.LOSS_RANK_REWARD_RANGE.first,
                    GameConstants.LOSS_RANK_REWARD_RANGE.last + 1
                ),
                goldDelta = random.nextInt(
                    GameConstants.LOSS_GOLD_REWARD_RANGE.first,
                    GameConstants.LOSS_GOLD_REWARD_RANGE.last + 1
                )
            )

            MatchOutcome.DRAW -> MatchRewards(rankPointsDelta = 0, goldDelta = GameConstants.LOSS_GOLD_REWARD_RANGE.last)
        }
    }
}
