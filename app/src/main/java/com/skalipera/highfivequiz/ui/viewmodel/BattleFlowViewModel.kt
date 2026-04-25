package com.skalipera.highfivequiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.skalipera.highfivequiz.core.MatchPhase
import com.skalipera.highfivequiz.domain.battle.BattleEvent
import com.skalipera.highfivequiz.domain.battle.BattleStateMachine
import com.skalipera.highfivequiz.ui.architecture.UiEffect
import com.skalipera.highfivequiz.ui.architecture.UiEvent
import com.skalipera.highfivequiz.ui.architecture.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class BattleFlowUiState(
    val phase: MatchPhase = MatchPhase.SETUP
) : UiState

sealed interface BattleFlowUiEvent : UiEvent {
    data class OnBattleEvent(val event: BattleEvent) : BattleFlowUiEvent
}

sealed interface BattleFlowUiEffect : UiEffect {
    data class NavigateToPhase(val phase: MatchPhase) : BattleFlowUiEffect
}

class BattleFlowViewModel : ViewModel() {

    private val machine = BattleStateMachine()

    private val _uiState = MutableStateFlow(BattleFlowUiState())
    val uiState: StateFlow<BattleFlowUiState> = _uiState.asStateFlow()

    fun onEvent(event: BattleFlowUiEvent) {
        when (event) {
            is BattleFlowUiEvent.OnBattleEvent -> {
                val next = machine.reduce(event.event)
                _uiState.value = _uiState.value.copy(phase = next)
            }
        }
    }
}
