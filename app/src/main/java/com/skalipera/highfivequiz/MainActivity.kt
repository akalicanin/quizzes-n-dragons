package com.skalipera.highfivequiz

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
import com.skalipera.highfivequiz.ui.UIRouter
import com.skalipera.highfivequiz.ui.theme.HighFiveQuizTheme
import com.skalipera.highfivequiz.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {

    // 1. Inicijalizacija ViewModela (ostaje isto)
    private val gameViewModel: GameViewModel by viewModels()

    // 2. Deklaracija NearbyController-a koji će koristiti ovaj ViewModel
    private lateinit var nearbyController: NearbyController

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- FULLSCREEN LOGIKA (Tvoj postojeći kod) ---
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
        // ----------------------------------------------

        // 3. Inicijalizacija kontrolera
        // Prosleđujemo 'this' (Context) i 'gameViewModel' kako bi kontroler mogao da menja stanja u UI-u
        nearbyController = NearbyController(this, gameViewModel)

        setContent {
            HighFiveQuizTheme {
                // 4. Prosleđujemo oba objekta u UIRouter.
                // Napomena: Moraćeš u fajlu gde ti je definisan UIRouter
                // da dodaš 'nearbyController: NearbyController' kao drugi parametar.
                UIRouter(gameViewModel, nearbyController)
            }
        }
    }
}