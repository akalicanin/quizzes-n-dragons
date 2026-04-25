package com.skalipera.highfivequiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.skalipera.highfivequiz.ui.architecture.UiEffect
import com.skalipera.highfivequiz.ui.architecture.UiEvent
import com.skalipera.highfivequiz.ui.architecture.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    fun onEvent(event: MainMenuUiEvent) {
        when (event) {
            MainMenuUiEvent.OnStartBattleClicked -> Unit
        }
    }
}
