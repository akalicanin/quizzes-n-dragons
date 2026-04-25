package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.skalipera.highfivequiz.ui.dialogs.ProfileDialog
import com.skalipera.highfivequiz.ui.dialogs.SettingsDialog
import com.skalipera.highfivequiz.viewmodel.GameViewModel

@Composable
fun UIRouter(viewModel: GameViewModel) {

    // Dialogs
    var showSettingsDialog by remember {mutableStateOf(false)}
    var showProfileDialog by remember {mutableStateOf(false)}

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            TopBar(
                nickname = viewModel.playerNickname,
                coinAmount = viewModel.playerCoinAmount,
                settingsClicked = { showSettingsDialog = true },
                profileClicked = { showProfileDialog = true }
            )

            Box(modifier = Modifier.weight(1f)) {
                when (viewModel.currentScreen) {
                    GameViewModel.ScreenType.HOME -> {
                        AmbientView(rank = viewModel.playerRank, dragonClicked = { })
                    }
                    GameViewModel.ScreenType.DRAGONS -> {
                        DragonsScreen(
                            "Temp",
                            "Temp",
                            "Temp",
                            5,
                            clickedForward = {},
                            clickedBackward = {}
                            )
                    }
                    GameViewModel.ScreenType.SHOP -> {
                        ShopScreen()
                    }
                    GameViewModel.ScreenType.QUIZ_ACTIVE -> {
                        //QuizScreen(                        )
                    }
                    GameViewModel.ScreenType.START_SCREEN -> {

                    }
                    GameViewModel.ScreenType.FINISH_SCREEN -> {

                    }
                }
            }

            // 3. BOTTOM BAR (Always visible, but hides during a Quiz)
            if (viewModel.currentScreen != GameViewModel.ScreenType.QUIZ_ACTIVE
                && viewModel.currentScreen != GameViewModel.ScreenType.START_SCREEN
                && viewModel.currentScreen != GameViewModel.ScreenType.FINISH_SCREEN) {
                BottomBar(
                    currentScreen = viewModel.currentScreen,
                    onButtonClicked = { newScreen ->
                        viewModel.navigateTo(newScreen)
                    }
                )
            }
        }
    }

    if (showSettingsDialog) {
        SettingsDialog(onDismiss =  {showSettingsDialog = false})
    }

    if (showProfileDialog) {
        ProfileDialog(viewModel.playerNickname,
            onSaveName = {newName -> viewModel.updateNickname(newName)},
            onDismiss = {showProfileDialog = false}
        )
    }
}