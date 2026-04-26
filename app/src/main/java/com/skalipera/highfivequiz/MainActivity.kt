package com.skalipera.highfivequiz

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skalipera.highfivequiz.ui.UIRouter
import com.skalipera.highfivequiz.ui.theme.HighFiveQuizTheme
import com.skalipera.highfivequiz.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(applicationContext)
    }

    private lateinit var nearbyController: NearbyController

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        window.insetsController?.let {
            it.hide(WindowInsets.Type.systemBars())
            it.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        nearbyController = NearbyController(this, gameViewModel)

        gameViewModel.sendNetworkMessage = { jsonString ->
            nearbyController.sendData(jsonString)
        }

        // Questions reading
        val jsonString = applicationContext.assets.open("quiz_questions.json")
            .bufferedReader().use { it.readText() }
        gameViewModel.loadQuestionBank(jsonString)

        setContent {
            HighFiveQuizTheme {
                UIRouter(gameViewModel, nearbyController)
            }
        }
    }
}

class GameViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            // Create the stats manager using the context, and pass it to the ViewModel
            val statsManager = PlayerStatsManager(context)
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(statsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}