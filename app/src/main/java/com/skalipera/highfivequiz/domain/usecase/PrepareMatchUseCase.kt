package com.skalipera.highfivequiz.domain.usecase

import com.skalipera.highfivequiz.core.QuestionCategory
import com.skalipera.highfivequiz.data.local.entity.BattleLoadoutEntity

class PrepareMatchUseCase {

    operator fun invoke(
        ownerProfileId: Long,
        selectedDragonIds: List<Long>,
        bannedCategory: QuestionCategory?
    ): BattleLoadoutEntity = BattleLoadoutEntity(
        ownerProfileId = ownerProfileId,
        selectedDragonIds = selectedDragonIds,
        bannedCategory = bannedCategory
    )
}
