package com.skalipera.highfivequiz.ui.utility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay

@Composable
fun TimedMessage(msg: String, isVisible: Boolean, onTimerFinished: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(), // It will smoothly fade in
            exit = fadeOut()  // It will smoothly fade out after 3 seconds
        ) {
            Text(msg)

            LaunchedEffect(key1 = isVisible) {
                delay(3000)
                onTimerFinished()
            }
        }
    }
}