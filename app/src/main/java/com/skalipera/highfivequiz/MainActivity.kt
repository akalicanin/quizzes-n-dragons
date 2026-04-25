package com.skalipera.highfivequiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.skalipera.highfivequiz.ui.navigation.HighFiveQuizApp
import com.skalipera.highfivequiz.ui.theme.HighFiveQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HighFiveQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    HighFiveQuizApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
