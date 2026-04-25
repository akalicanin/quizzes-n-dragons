package com.skalipera.highfivequiz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skalipera.highfivequiz.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        // Wait a second so the user can see "Opponent found!" on the button
        viewModelScope.launch {
            delay(2000)
            currentScreen = ScreenType.START_SCREEN
        }
    }

    enum class ScreenType() {
        DRAGONS,
        HOME,
        SHOP,
        DRAGON_SELECT,
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

    // DRAGON DATA
    data class Dragon(
        val id: String,
        val name: String,
        val imageResId: Int // The R.drawable ID for its image
    )

    val allDragons = listOf(
        Dragon("dragon_01", "MathAndPhys", R.drawable.dragon_math),
        Dragon("dragon_02", "ChemAndBio", R.drawable.dragon_science),
        Dragon("dragon_03", "GeoAndHist", R.drawable.dragon_geohistory),
        Dragon("dragon_04", "Hacker", R.drawable.dragon_hacker)
    )

    val myDragons = listOf(
        Dragon("dragon_01", "MathAndPhys", R.drawable.dragon_math),
        Dragon("dragon_02", "ChemAndBio", R.drawable.dragon_science),
        Dragon("dragon_03", "GeoAndHist", R.drawable.dragon_geohistory),
        Dragon("dragon_04", "Hacker", R.drawable.dragon_hacker)
    )

    // Screen state
    var currentScreen by mutableStateOf(ScreenType.HOME)
        private set

    // Notification message state
    var isMessageVisible by mutableStateOf(false)
        private set
    var currentMessageText by mutableStateOf("Uspesno povezano!")
        private set

    fun dismissMessage() {
        isMessageVisible = false
    }

    // Player data state
    val myNearbyId = (1000..9999).random().toString() // Unique ID for this session
    var playerNickname by mutableStateOf("Barcelona")
        private set
    var playerRank by mutableStateOf(0)
        private set
    var playerCoinAmount by mutableStateOf(0)
        private set
    var selectedDragon by mutableStateOf(myDragons[0])
        private set

    // 3. A function to change the dragon and go back to the home screen
    fun equipDragon(dragon: Dragon) {
        selectedDragon = dragon
        currentScreen = ScreenType.HOME // Instantly go back to home screen!
    }

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