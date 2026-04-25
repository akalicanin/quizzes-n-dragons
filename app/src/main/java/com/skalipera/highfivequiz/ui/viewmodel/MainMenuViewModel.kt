package com.skalipera.highfivequiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.skalipera.highfivequiz.ui.architecture.UiEffect
import com.skalipera.highfivequiz.ui.architecture.UiEvent
import com.skalipera.highfivequiz.ui.architecture.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow

data class MainMenuUiState(
    val playerName: String = "Player",
    val selectedDragonName: String = "Starter Dragon"
) : UiState

sealed interface MainMenuUiEvent : UiEvent {
    data object OnStartBattleClicked : MainMenuUiEvent
}

sealed interface MainMenuUiEffect : UiEffect {
    data object NavigateToBattleSetup : MainMenuUiEffect
}

class MainMenuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainMenuUiState())
    val uiState: StateFlow<MainMenuUiState> = _uiState.asStateFlow()
    private val _effects = MutableSharedFlow<MainMenuUiEffect>(extraBufferCapacity = 1)
    val effects: SharedFlow<MainMenuUiEffect> = _effects.asSharedFlow()

    fun onEvent(event: MainMenuUiEvent) {
        when (event) {
            MainMenuUiEvent.OnStartBattleClicked -> {
                _effects.tryEmit(MainMenuUiEffect.NavigateToBattleSetup)
            }
        }
    }
}
