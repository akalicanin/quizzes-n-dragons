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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.skalipera.highfivequiz.NearbyController
import com.skalipera.highfivequiz.R
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
                        // Compare the order of the screens to find the direction
                        if (targetState.ordinal > initialState.ordinal) {
                            // to the right
                            (slideInHorizontally { fullWidth -> fullWidth }) togetherWith
                                    (slideOutHorizontally { fullWidth -> -fullWidth })
                        } else {
                            // to the left
                            (slideInHorizontally { fullWidth -> -fullWidth }) togetherWith
                                    (slideOutHorizontally { fullWidth -> fullWidth })
                        }
                    },
                    label = "Screen Transition"
                ) { screenToDraw ->
                    when (screenToDraw) {
                        GameViewModel.ScreenType.HOME -> {
                            AmbientView(
                                rank = viewModel.playerRank,
                                viewModel.selectedDragon.imageResId,
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
                                dragonClicked = {},
                                openDragonSelection = {
                                    viewModel.navigateTo(GameViewModel.ScreenType.DRAGON_SELECT)
                                }
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
                        GameViewModel.ScreenType.DRAGON_SELECT -> {
                            DragonSelectScreen(
                                dragons = viewModel.myDragons,
                                onDragonSelected = { chosenDragon ->
                                    viewModel.equipDragon(chosenDragon) // Tells ViewModel to save and go back!
                                }
                            )
                        }
                        GameViewModel.ScreenType.QUIZ_ACTIVE -> {
                            //QuizScreen(                        )
                        }
                        GameViewModel.ScreenType.START_SCREEN -> {

                        }
                        GameViewModel.ScreenType.BATTLE_SCREEN -> {

                        }
                        GameViewModel.ScreenType.FINISH_SCREEN -> {

                        }
                    }
                }

//                when (viewModel.currentScreen) {
//                    GameViewModel.ScreenType.HOME -> {
//                        AmbientView(rank = viewModel.playerRank, dragonClicked = { })
//                    }
//                    GameViewModel.ScreenType.DRAGONS -> {
//                        DragonsScreen(
//                            "Denaprom",
//                            "Mathematical, LEGENDARY",
//                            "Mathematical Dragon Type from kragujevac aleksandar denovic",
//                            5,
//                            clickedForward = {},
//                            clickedBackward = {}
//                            )
//                    }
//                    GameViewModel.ScreenType.SHOP -> {
//                        ShopScreen()
//                    }
//                    GameViewModel.ScreenType.QUIZ_ACTIVE -> {
//                        //QuizScreen(                        )
//                    }
//                    GameViewModel.ScreenType.START_SCREEN -> {
//
//                    }
//                    GameViewModel.ScreenType.BATTLE_SCREEN -> {
//
//                    }
//                    GameViewModel.ScreenType.FINISH_SCREEN -> {
//
//                    }
//                }
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

    // Prikaz poruke o uspešnom povezivanju
    if (viewModel.isMessageVisible) {
        LaunchedEffect(viewModel.isMessageVisible) {
            android.util.Log.d("UIRouter", "Showing success toast: ${viewModel.currentMessageText}")
            Toast.makeText(context, viewModel.currentMessageText, Toast.LENGTH_LONG).show()
            kotlinx.coroutines.delay(1000) // Give it time to display before resetting state
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