package com.skalipera.highfivequiz.domain.usecase

import com.skalipera.highfivequiz.domain.model.ClashResolution

class ApplyClashDamageUseCase {

    operator fun invoke(
        attackerScore: Int,
        defenderScore: Int,
        defenderDragonHp: Int,
        defenderPlayerHp: Int
    ): ClashResolution {
        val netDamage = (attackerScore - defenderScore).coerceAtLeast(0)
        val dragonHpAfter = (defenderDragonHp - netDamage).coerceAtLeast(0)
        val overflow = (netDamage - defenderDragonHp).coerceAtLeast(0)
        val playerHpAfter = (defenderPlayerHp - overflow).coerceAtLeast(0)

        return ClashResolution(
            netDamageToDefender = netDamage,
            defenderDragonHpAfter = dragonHpAfter,
            defenderPlayerHpAfter = playerHpAfter,
            attackerWonClash = attackerScore > defenderScore
        )
    }
}
