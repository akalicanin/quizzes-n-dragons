package com.skalipera.highfivequiz.ui

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.skalipera.highfivequiz.NearbyController
import com.skalipera.highfivequiz.ui.dialogs.ProfileDialog
import com.skalipera.highfivequiz.ui.dialogs.SettingsDialog
import com.skalipera.highfivequiz.viewmodel.GameViewModel

@Composable
fun UIRouter(viewModel: GameViewModel, nearbyController: NearbyController) { // 1. DODAT PARAMETAR

    val context = LocalContext.current
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showProfileDialog by remember { mutableStateOf(false) }

    // 2. LAUNCHER ZA DOZVOLE
    // Ovo se pokreće kada korisnik klikne na Play dugme
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            // Ako su sve dozvole date, pokreni pretragu iz kontrolera
            nearbyController.startPlay()
        } else {
            Toast.makeText(context, "Dozvole su neophodne za igru u dvoje!", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray)) {
        Column(modifier = Modifier.fillMaxSize()) {

            TopBar(
                nickname = viewModel.playerNickname,
                coinAmount = viewModel.playerCoinAmount,
                settingsClicked = { showSettingsDialog = true },
                profileClicked = { showProfileDialog = true }
            )

            Box(modifier = Modifier.weight(1f)) {

                AnimatedContent(
                    targetState = viewModel.currentScreen,
                    transitionSpec = {
                        if (targetState.ordinal > initialState.ordinal) {
                            (slideInHorizontally { fullWidth -> fullWidth }) togetherWith
                                    (slideOutHorizontally { fullWidth -> -fullWidth })
                        } else {
                            (slideInHorizontally { fullWidth -> -fullWidth }) togetherWith
                                    (slideOutHorizontally { fullWidth -> fullWidth })
                        }
                    },
                    label = "Screen Transition"
                ) { screenToDraw ->
                    when (screenToDraw) {
                        GameViewModel.ScreenType.HOME -> {
                            // 3. POVEZIVANJE PLAY DUGMETA
                            AmbientView(
                                rank = viewModel.playerRank,
                                startMatching = {
                                    // Kada klikne Play, prvo proveravamo dozvole
                                    permissionLauncher.launch(arrayOf(
                                        Manifest.permission.BLUETOOTH_SCAN,
                                        Manifest.permission.BLUETOOTH_ADVERTISE,
                                        Manifest.permission.BLUETOOTH_CONNECT,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.NEARBY_WIFI_DEVICES
                                    ))
                                },
                                dragonClicked = { }
                            )
                        }
                        GameViewModel.ScreenType.DRAGONS -> {
                            DragonsScreen(
                                "Denaprom",
                                "Mathematical, LEGENDARY",
                                "Mathematical Dragon Type from kragujevac aleksandar denovic",
                                5,
                                clickedForward = {},
                                clickedBackward = {}
                            )
                        }
                        GameViewModel.ScreenType.SHOP -> {
                            ShopScreen()
                        }
                        GameViewModel.ScreenType.QUIZ_ACTIVE -> { /* QuizScreen(...) */ }
                        GameViewModel.ScreenType.START_SCREEN -> { /* Start screen logic */ }
                        GameViewModel.ScreenType.BATTLE_SCREEN -> { /* Battle screen logic */ }
                        GameViewModel.ScreenType.FINISH_SCREEN -> { /* Finish screen logic */ }
                    }
                }
            }

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

    // Prikaz poruke o uspešnom povezivanju
    if (viewModel.isMessageVisible) {
        LaunchedEffect(viewModel.isMessageVisible) {
            Toast.makeText(context, viewModel.currentMessageText, Toast.LENGTH_SHORT).show()
            viewModel.dismissMessage()
        }
    }

    if (showSettingsDialog) {
        SettingsDialog(onDismiss = { showSettingsDialog = false })
    }

    if (showProfileDialog) {
        ProfileDialog(viewModel.playerNickname,
            onSaveName = { newName -> viewModel.updateNickname(newName) },
            onDismiss = { showProfileDialog = false }
        )
    }
}