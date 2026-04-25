package com.skalipera.highfivequiz.domain

import com.skalipera.highfivequiz.core.DragonType
import com.skalipera.highfivequiz.data.local.entity.DragonEntity
import com.skalipera.highfivequiz.domain.usecase.ValidateLoadoutUseCase
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateLoadoutUseCaseTest {

    private val useCase = ValidateLoadoutUseCase()

    @Test
    fun `returns true when three dragons selected and max two same type`() {
        val dragons = listOf(
            DragonEntity(id = 1, name = "a", type = DragonType.BIO),
            DragonEntity(id = 2, name = "b", type = DragonType.BIO),
            DragonEntity(id = 3, name = "c", type = DragonType.HISTORY)
        )

        assertTrue(useCase(dragons))
    }

    @Test
    fun `returns false when more than two dragons share same type`() {
        val dragons = listOf(
            DragonEntity(id = 1, name = "a", type = DragonType.BIO),
            DragonEntity(id = 2, name = "b", type = DragonType.BIO),
            DragonEntity(id = 3, name = "c", type = DragonType.BIO)
        )

        assertFalse(useCase(dragons))
    }

    @Test
    fun `returns false when selected dragon count is not three`() {
        val dragons = listOf(
            DragonEntity(id = 1, name = "a", type = DragonType.BIO),
            DragonEntity(id = 2, name = "b", type = DragonType.HISTORY)
        )

        assertFalse(useCase(dragons))
    }
}
