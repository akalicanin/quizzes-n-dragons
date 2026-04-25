package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skalipera.highfivequiz.ui.utility.TimedMessage

@Composable
fun GameScreen() {

    // Notification message state
    var isMessageVisible by remember { mutableStateOf(false) }
    var currentMessageText by remember { mutableStateOf("") }

    // Player data state
    var playerNickname by remember { mutableStateOf("DragonSlayer") }
    var playerRank by remember { mutableStateOf(1420) }
    var playerCoinAmount by remember { mutableStateOf(450) }

    // THE ROOT BOX: Allows us to layer the notification over the game
    Box(modifier = Modifier.fillMaxSize()) {

        // LAYER 1: The Main Game UI (Flows top to bottom)
        Column(modifier = Modifier.fillMaxSize()) {

            // Top Bar
            TopBar(
                nickname = playerNickname,
                coinAmount = playerCoinAmount,
                settingsClicked = { /* TODO */ },
                profileClicked = { /* TODO */ }
            )

            // Middle: Ambient View (Uses weight to fill all remaining space!)
            AmbientView(
                rank = playerRank,
                dragonClicked = {
                    currentMessageText = "You poked your dragon!"
                    isMessageVisible = true
                },
                modifier = Modifier.weight(1f)
            )

            // Bottom Bar
            BottomBar(
                onButtonClicked = { incomingMessage ->
                    currentMessageText = incomingMessage
                    isMessageVisible = true
                }
            )
        }

        // LAYER 2: The Floating Notification (Drawn AFTER the Column, so it sits on top)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp) // Pushes it just below the TopBar
        ) {
            TimedMessage(
                msg = currentMessageText,
                isVisible = isMessageVisible,
                onTimerFinished = { isMessageVisible = false }
            )
        }
    }
}