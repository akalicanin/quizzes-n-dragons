package com.skalipera.highfivequiz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    var isSearching by mutableStateOf(false)
        private set

    var isConnected by mutableStateOf(false)
        private set

    var opponentName by mutableStateOf<String?>(null)
        private set

    // Funkcije koje kontroler poziva da osveži UI
    fun onSearchingStatusChanged(searching: Boolean) {
        isSearching = searching
    }

    fun onConnectionSuccess(name: String) {
        isConnected = true
        isSearching = false
        opponentName = name
        currentMessageText = "Uspešno povezan sa: $name"
        isMessageVisible = true
        // Opciono: Automatski prebaci na ekran za početak kviza
        // currentScreen = ScreenType.START_SCREEN
    }

    enum class ScreenType() {
        DRAGONS,
        HOME,
        SHOP,
        QUIZ_ACTIVE,
        BATTLE_SCREEN,
        START_SCREEN,
        FINISH_SCREEN
    }

    enum class QuestionTopic() {
        MATEMATIKA,
        GEOGRAFIJA,
        ISTORIJA,
        BIOLOGIJA,
        HEMIJA,
        POP_KULTURA
    }

    // Question class
    data class Question(
        val topic : QuestionTopic,
        val text : String,
        val answers : List<String>,
        val correctAnswer : Int
    )

    // Screen state
    var currentScreen by mutableStateOf(ScreenType.HOME)
        private set

    // Notification message state
    var isMessageVisible by mutableStateOf(false)
        private set
    var currentMessageText by mutableStateOf("Uspesno povezano!")
        private set

    // Player data state
    var playerNickname by mutableStateOf("Barcelona")
        private set
    var playerRank by mutableStateOf(0)
        private set
    var playerCoinAmount by mutableStateOf(0)
        private set

    // functions called from UI
    fun navigateTo(screen: ScreenType) {
        currentScreen = screen
    }

    fun updateNickname(newName: String) {
        if (newName.isNotBlank() && newName.length <= 12) {
            playerNickname = newName
            // save to database
        }
    }

    fun submitQuizAnswer(answer: String) {
        // TODO: Check if answer is correct, update score, move to next question
    }

}