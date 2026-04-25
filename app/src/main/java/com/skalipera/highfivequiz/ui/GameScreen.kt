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
import com.skalipera.highfivequiz.ui.dialogs.ProfileDialog
import com.skalipera.highfivequiz.ui.dialogs.SettingsDialog
import com.skalipera.highfivequiz.ui.utility.TimedMessage
import com.skalipera.highfivequiz.viewmodel.GameViewModel

@Composable
fun GameScreen() {

    // Notification message state
    var isMessageVisible by remember { mutableStateOf(false) }
    var currentMessageText by remember { mutableStateOf("") }

    // Player data state
    var playerNickname by remember { mutableStateOf("DragonSlayer") }
    var playerRank by remember { mutableStateOf(1420) }
    var playerCoinAmount by remember { mutableStateOf(450) }

    // Dialogs
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showProfileDialog by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {

        //Main UI column
        Column(modifier = Modifier.fillMaxSize()) {

            // Top Bar
            TopBar(
                nickname = playerNickname,
                coinAmount = playerCoinAmount,
                settingsClicked = { showSettingsDialog = true },
                profileClicked = { showProfileDialog = true }
            )

            // Game backgroound with dragon
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
                GameViewModel.ScreenType.START_SCREEN,
                onButtonClicked = {
                }
            )
        }

        if (showSettingsDialog) {
            SettingsDialog(onDismiss =  {showSettingsDialog = false})
        }

        if (showProfileDialog) {
            ProfileDialog(playerNickname,
                onSaveName = {newName -> playerNickname = newName},
                onDismiss = {showProfileDialog = false}
            )
        }

        //Floating notification
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
        ) {
            TimedMessage(
                msg = currentMessageText,
                isVisible = isMessageVisible,
                onTimerFinished = { isMessageVisible = false }
            )
        }
    }
}