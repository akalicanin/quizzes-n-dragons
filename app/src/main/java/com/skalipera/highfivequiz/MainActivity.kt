package com.skalipera.highfivequiz

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.skalipera.highfivequiz.ui.GameScreen
import com.skalipera.highfivequiz.ui.QuizScreen
import com.skalipera.highfivequiz.ui.theme.HighFiveQuizTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        window.attributes.layoutInDisplayCutoutMode =
            android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        window.insetsController?.let {
            it.hide(WindowInsets.Type.systemBars())
            it.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            HighFiveQuizTheme {
                //GameScreen()
                QuizScreen("Geografija", "Gde je Srbija", listOf("Evropa", "Afrika", "Azija", "Juzna Amerika"), 2, listOf(true, false, true, false),10, onAnswerSelected = {})
            }
        }
    }


//    @RequiresApi(Build.VERSION_CODES.R)
//    private fun hideSystemUI() {
////        window.insetsController?.let { controller ->
////            controller.hide(WindowInsets.Type.navigationBars())
////            controller.systemBarsBehavior =
////                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
////        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            // Android 11+
//            window.insetsController?.let { controller ->
//                controller.hide(WindowInsets.Type.systemBars())
//                controller.systemBarsBehavior =
//                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        } else {
//            // Android 7–10
//            window.decorView.systemUiVisibility = (
//                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                            or View.SYSTEM_UI_FLAG_FULLSCREEN
//                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    )
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.R)
//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        if (hasFocus) {
//            hideSystemUI()
//        }
//    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HighFiveQuizTheme {
        Greeting("Android")
    }
}