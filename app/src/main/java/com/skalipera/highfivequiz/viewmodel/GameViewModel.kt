package com.skalipera.highfivequiz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.skalipera.highfivequiz.R
import com.skalipera.highfivequiz.ui.utility.GamePayload
import com.skalipera.highfivequiz.ui.utility.PayloadType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class GameViewModel : ViewModel() {
    enum class ScreenType() {
        DRAGONS,
        HOME,
        SHOP,
        DRAGON_SELECT,
        QUIZ_ACTIVE,
        WAITING_FOR_OPPONENT,
        BATTLE_SCREEN,
        BATTLE_BUMP,
        START_SCREEN,
        WIN_SCREEN,
        LOSE_SCREEN,
        DRAW_SCREEN
    }

    enum class QuestionTopic() {
        GEOHISTORY,
        MATH,
        SCIENCE,
        HACKER,
        POP_CULTURE
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
        val imageResId: Int,
        val type: QuestionTopic,
        val description: String = "This dragon is a master of its craft, waiting for a worthy challenger."
    )

    val allDragons = listOf(
        Dragon("dragon_01", "Tesladrag", R.drawable.dragon_math, QuestionTopic.MATH, "Born from the equations of ancient scholars, this dragon controls the laws of physics."),
        Dragon("dragon_02", "Bioweapon", R.drawable.dragon_science, QuestionTopic.SCIENCE, "A biological marvel, it uses chemical reactions to breathe iridescent flames."),
        Dragon("dragon_03", "Indiana Dragones", R.drawable.dragon_geohistory, QuestionTopic.GEOHISTORY, "It has seen the rise and fall of empires, carrying the weight of history in its scales."),
        Dragon("dragon_04", "Mr. Robodragon", R.drawable.dragon_hacker, QuestionTopic.HACKER, "A digital phantom that lives within the code. It can manipulate any system it encounters."),
        Dragon("dragon_05", "Joy", R.drawable.dragon_musicandart, QuestionTopic.POP_CULTURE, "Inspired by the greatest artists, its roar sounds like a symphony of pure inspiration.")
    )

    val myDragons = listOf(
        Dragon("dragon_01", "MathAndPhys", R.drawable.dragon_math, QuestionTopic.MATH, "Born from the equations of ancient scholars, this dragon controls the laws of physics."),
        Dragon("dragon_02", "ChemAndBio", R.drawable.dragon_science, QuestionTopic.SCIENCE, "A biological marvel, it uses chemical reactions to breathe iridescent flames."),
        Dragon("dragon_03", "GeoAndHist", R.drawable.dragon_geohistory, QuestionTopic.GEOHISTORY, "It has seen the rise and fall of empires, carrying the weight of history in its scales."),
        Dragon("dragon_04", "Hacker", R.drawable.dragon_hacker, QuestionTopic.HACKER, "A digital phantom that lives within the code. It can manipulate any system it encounters."),
        Dragon("dragon_05", "Culture", R.drawable.dragon_musicandart, QuestionTopic.POP_CULTURE, "Inspired by the greatest artists, its roar sounds like a symphony of pure inspiration.")
    )

    // Gallery state
    var galleryIndex by mutableStateOf(0)
        private set

    fun nextDragon() {
        if (galleryIndex < allDragons.size - 1) {
            galleryIndex++
        } else {
            galleryIndex = 0 // Wrap around
        }
    }

    fun prevDragon() {
        if (galleryIndex > 0) {
            galleryIndex--
        } else {
            galleryIndex = allDragons.size - 1 // Wrap around
        }
    }

    // Screen state
    var currentScreen by mutableStateOf(ScreenType.HOME)
        private set

    // Notification message state
    var isMessageVisible by mutableStateOf(false)
        private set
    var currentMessageText by mutableStateOf("Connected successfully!")
        private set
    fun dismissMessage() {
        isMessageVisible = false
    }

    // Player data state
    val myNearbyId = (1000..9999).random().toString() // Unique ID
    var playerNickname by mutableStateOf("Barcelona")
        private set
    var playerRank by mutableStateOf(0)
        private set
    var playerCoinAmount by mutableStateOf(0)
        private set
    var selectedDragon by mutableStateOf(myDragons[0])
        private set

    fun equipDragon(dragon: Dragon) {
        selectedDragon = dragon
        navigateTo(ScreenType.HOME)
    }

    // Multiplayer states
    var isHost by mutableStateOf(false)
        private set

    // The Controller will listen to this lambda to send data over the network
    var sendNetworkMessage: ((String) -> Unit)? = null

    // Game loop variables
    var currentQuestions by mutableStateOf<List<Question>>(emptyList())
        private set
    var currentQuestionIndex by mutableStateOf(0)
        private set
    var pastQuestionTopics by mutableStateOf<MutableList<QuestionTopic>>(mutableListOf())
        private set
    var timeRemaining by mutableStateOf(60) // in seconds
        private set

    // Multiplayer functions and variables
    var isSearching by mutableStateOf(false)
        private set
    var isConnected by mutableStateOf(false)
        private set
    var opponentName by mutableStateOf<String?>(null)
        private set
    fun onSearchingStatusChanged(searching: Boolean) {
        isSearching = searching
    }
    fun onConnectionSuccess(name: String, amIHost: Boolean) {
        isConnected = true
        isSearching = false
        opponentName = name
        isHost = amIHost
        currentMessageText = "Matched with: $name!"
        isMessageVisible = true
        navigateTo(ScreenType.START_SCREEN)

        val payload = GamePayload(PayloadType.DRAGON_ID, selectedDragon.id)
        sendNetworkMessage?.invoke(Gson().toJson(payload))

        if (isHost) {
            generateAndSendNextRound()
        }

    }

    // Player Health & Scores
    var myHp by mutableStateOf(30)
        private set
    var opponentHp by mutableStateOf(30)
        private set
    var myRoundScore by mutableStateOf(0)
        private set
    var opponentRoundScore by mutableStateOf(-1) // -1 means they haven't finished the round yet
        private set
    var opponentDragon by mutableStateOf<Dragon>(allDragons[0])
        private set

    // Called by NetworkLayer when opponent sends their dragon choice
    fun onOpponentDragonReceived(dragonId: String) {
        // Find the dragon in allDragons list and set it
        // PRILICNO SAM SIGURAN DA NE MOZE DA BUDE NULL!!!
        opponentDragon = allDragons.find { it.id == dragonId }!!

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

    fun submitQuizAnswer(answerIndex: Int) {
        val currentQ = currentQuestions[currentQuestionIndex]
        if (answerIndex == currentQ.correctAnswer) {
            myRoundScore += 1
        }

        if (currentQuestionIndex < currentQuestions.size - 1) {
            // Next question
            currentQuestionIndex += 1
        } else {
            // Finished all 7 questions!
            finishRoundLocally()
        }
    }

    fun resetGame() {
        myHp = 30
        opponentHp = 30
        myRoundScore = 0
        opponentRoundScore = -1
        currentQuestions = emptyList()
        currentQuestionIndex = 0
        opponentDragon = allDragons[0]
        pastQuestionTopics = mutableListOf()
    }
    // TODO(rematch implementation: dont disconnect opponent here)
    fun onOpponentDisconnected() {
        isConnected = false
        isSearching = false
        opponentName = null
        isHost = false
        resetGame()
        navigateTo(ScreenType.HOME)
        currentMessageText = "Opponent disconnected!"
        isMessageVisible = true
    }

    fun abortMultiplayer() {
        isConnected = false
        isSearching = false
        opponentName = null
        isHost = false
        resetGame()
        navigateTo(ScreenType.HOME)
    }

    fun generateAndSendNextRound() {
        if (!isHost) return

        val topics = QuestionTopic.entries.toSet()
        val remainingList = topics.subtract(pastQuestionTopics.toSet())

        val topic = remainingList.toTypedArray().random()
        pastQuestionTopics.add(topic)

        // Load 7 random questions
        val allQuestionsForTopic = getQuestionsForTopic(topic)
        val selectedQuestions = allQuestionsForTopic.shuffled().take(7)

        val questionsJsonString = Gson().toJson(selectedQuestions)
        val payload = GamePayload(PayloadType.QUESTIONS, questionsJsonString)
        sendNetworkMessage?.invoke(Gson().toJson(payload))

        // Set them locally
        onQuestionsReceivedFromHost(selectedQuestions)
    }

    private fun getQuestionsForTopic(topic: QuestionTopic) : List<Question> {
        TODO("Not yet implemented")
        return emptyList()
    }

    private fun finishRoundLocally() {
        navigateTo(ScreenType.WAITING_FOR_OPPONENT)

        val payload = GamePayload(PayloadType.ROUND_SCORE, myRoundScore.toString())
        sendNetworkMessage?.invoke(Gson().toJson(payload))

        checkIfBothFinished()
    }

    // Host generated questions and sent them to us (the Client)
    fun onQuestionsReceivedFromHost(questions: List<Question>) {
        currentQuestions = questions
        currentQuestionIndex = 0
        myRoundScore = 0
        opponentRoundScore = -1
        navigateTo(ScreenType.QUIZ_ACTIVE)
        startTimer()
    }

    // Opponent finished their 7 questions and sent us their score
    fun onOpponentFinishedRound(score: Int) {
        opponentRoundScore = score
        checkIfBothFinished()
    }

    // Called when the Accelerometer detects a physical bump!
    fun onPhysicalBumpDetected() {
        if (currentScreen == ScreenType.BATTLE_SCREEN) {
            resolveCombat()
        }
    }

    private fun checkIfBothFinished() {
        // If I am waiting, and the opponent has sent a valid score
        if (currentScreen == ScreenType.WAITING_FOR_OPPONENT && opponentRoundScore >= 0) {
            // Both are done! Time to bump phones.
            navigateTo(ScreenType.BATTLE_SCREEN)
        }
    }

    private fun startTimer() {
        timeRemaining = 60
        viewModelScope.launch {
            while (timeRemaining > 0 && currentScreen == ScreenType.QUIZ_ACTIVE) {
                delay(1000)
                timeRemaining -= 1
            }
            if (currentScreen == ScreenType.QUIZ_ACTIVE) {
                finishRoundLocally() // Time ran out!
            }
        }
    }
    private fun resolveCombat() {
        navigateTo(ScreenType.BATTLE_BUMP)

        // Calculate Damage (Score * 10)
        var myDamage = myRoundScore * 0.5
        var opponentDamage = opponentRoundScore * 0.5

        // Apply Dragon buffs if types match (Add logic here later)
        if (selectedDragon.type == currentQuestions.get(1).topic) {
            myDamage *= 1.10
        }
        if (opponentDragon.type == currentQuestions.get(1).topic) {
            opponentDamage *= 1.10
        }


        // Subtract HP
        opponentHp -= myDamage.roundToInt()
        myHp -= opponentDamage.roundToInt()

        // Wait a few seconds to show animation, then check for game over or next round
        viewModelScope.launch {
            delay(3000)
            if (myHp <= 0 || opponentHp <= 0 || pastQuestionTopics.count() == QuestionTopic.entries.count()) {
                if (myHp <= 0) {
                    navigateTo(ScreenType.LOSE_SCREEN)
                    playerRank -= 3
                }
                else if (opponentHp <= 0) {
                    navigateTo(ScreenType.WIN_SCREEN)
                    playerRank += 3
                }
                else if (pastQuestionTopics.count() == QuestionTopic.entries.count()) {
                    navigateTo(ScreenType.DRAW_SCREEN)
                }
            } else {
                if (isHost) {
                    generateAndSendNextRound()
                }
            }
        }
    }
}