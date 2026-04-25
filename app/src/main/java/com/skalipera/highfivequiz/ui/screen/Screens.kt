package com.skalipera.highfivequiz.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
private fun PlaceholderScreen(title: String, onContinue: (() -> Unit)? = null) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title)
        onContinue?.let {
            Button(onClick = it) {
                Text("Continue")
            }
        }
    }
}

@Composable
fun MainMenuScreen(onContinue: () -> Unit) = PlaceholderScreen("Main Menu / Pet Care", onContinue)

@Composable
fun DragonCollectionScreen(onContinue: () -> Unit) = PlaceholderScreen("Dragon Collection & Customization", onContinue)

@Composable
fun LoadoutBuilderScreen(onContinue: () -> Unit) = PlaceholderScreen("Deck / Loadout Builder", onContinue)

@Composable
fun BanCategoryScreen(onContinue: () -> Unit) = PlaceholderScreen("Ban Category Selection", onContinue)

@Composable
fun NfcLobbyScreen(onContinue: () -> Unit) = PlaceholderScreen("NFC Lobby (Tap 1)", onContinue)

@Composable
fun TriviaRoundScreen(onContinue: () -> Unit) = PlaceholderScreen("Trivia Round", onContinue)

@Composable
fun WaitingScreen(onContinue: () -> Unit) = PlaceholderScreen("Waiting For Opponent", onContinue)

@Composable
fun DragonAttackSelectionScreen(onContinue: () -> Unit) = PlaceholderScreen("Dragon Attack Selection", onContinue)

@Composable
fun NfcClashScreen(onContinue: () -> Unit) = PlaceholderScreen("NFC Clash (Tap 2)", onContinue)

@Composable
fun RoundResultScreen(onContinue: () -> Unit) = PlaceholderScreen("Round / Match Result", onContinue)

@Composable
fun RewardsScreen(onContinue: () -> Unit) = PlaceholderScreen("Rewards + Rematch", onContinue)
