package com.skalipera.highfivequiz.domain.usecase

import com.skalipera.highfivequiz.core.GameConstants
import com.skalipera.highfivequiz.data.local.entity.DragonEntity

class ValidateLoadoutUseCase {

    operator fun invoke(selectedDragons: List<DragonEntity>): Boolean {
        if (selectedDragons.size != GameConstants.MAX_DRAGONS_PER_MATCH) return false
        val typeCounts = selectedDragons.groupingBy { it.type }.eachCount()
        return typeCounts.values.all { it <= GameConstants.MAX_SAME_TYPE_DRAGONS_PER_MATCH }
    }
}
