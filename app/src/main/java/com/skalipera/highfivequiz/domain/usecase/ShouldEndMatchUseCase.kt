package com.skalipera.highfivequiz.domain.usecase

class ShouldEndMatchUseCase {

    operator fun invoke(playerHp: Int, aliveDragonCount: Int): Boolean =
        playerHp <= 0 || aliveDragonCount <= 0
}
