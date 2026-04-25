package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.skalipera.highfivequiz.ui.utility.TimedMessage

@Composable
fun GameScreen() {

    //notification message
    var isMessageVisible by remember { mutableStateOf(false) }
    var currentMessageText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.align(Alignment.TopCenter).padding(50.dp)) {
            TimedMessage(
                currentMessageText,
                isMessageVisible,
                onTimerFinished = {isMessageVisible = false}
            )
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomBar(onButtonClicked = {
                incomingMessage -> currentMessageText = incomingMessage
                isMessageVisible = true
            })
        }
    }
}