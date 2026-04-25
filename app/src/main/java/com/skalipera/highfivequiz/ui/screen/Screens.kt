package com.skalipera.highfivequiz.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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
fun MainMenuScreen(onContinue: () -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showNameDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showBattleDialog by remember { mutableStateOf(false) }
    var showBanCategoryDialog by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(false) }
    var playerName by remember { mutableStateOf("Player") }
    var editedPlayerName by remember { mutableStateOf(playerName) }
    var selectedMenuPage by remember { mutableIntStateOf(0) }
    var bannedCategory by remember { mutableStateOf<String?>(null) }
    val pageTitles = listOf("Battle options", "Shop", "Your dragons")
    val categories = listOf("History", "Sports", "Science", "Art")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Button(onClick = { menuExpanded = true }) {
                Text("☰")
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Change player name") },
                    onClick = {
                        editedPlayerName = playerName
                        showNameDialog = true
                        menuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(if (isMuted) "Unmute game" else "Mute game") },
                    onClick = {
                        isMuted = !isMuted
                        menuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("About app") },
                    onClick = {
                        showAboutDialog = true
                        menuExpanded = false
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = playerName,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 48.dp, vertical = 56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Dragon Sprite Placeholder",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isMuted) "Sound: muted" else "Sound: on",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.2f),
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        selectedMenuPage =
                            if (selectedMenuPage == 0) pageTitles.lastIndex else selectedMenuPage - 1
                    }
                ) { Text("←") }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = pageTitles[selectedMenuPage],
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    when (selectedMenuPage) {
                        0 -> {
                            Button(onClick = { showBattleDialog = true }) {
                                Text("Open battle menu")
                            }
                            bannedCategory?.let {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Banned category: $it",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        1 -> Text("Shop coming soon")
                        2 -> Button(onClick = onContinue) { Text("Open your dragons") }
                    }
                }

                Button(
                    onClick = { selectedMenuPage = (selectedMenuPage + 1) % pageTitles.size }
                ) { Text("→") }
            }
        }
    }

    if (showNameDialog) {
        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text("Change player name") },
            text = {
                OutlinedTextField(
                    value = editedPlayerName,
                    onValueChange = { editedPlayerName = it },
                    label = { Text("Player name") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        playerName = editedPlayerName.ifBlank { "Player" }
                        showNameDialog = false
                    }
                ) { Text("Save") }
            },
            dismissButton = {
                Button(onClick = { showNameDialog = false }) { Text("Cancel") }
            }
        )
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = { Text("About the app") },
            text = {
                Text("High Five Quiz combines dragon pets, trivia, and NFC battle interactions.")
            },
            confirmButton = {
                Button(onClick = { showAboutDialog = false }) { Text("Close") }
            }
        )
    }

    if (showBattleDialog) {
        AlertDialog(
            onDismissRequest = { showBattleDialog = false },
            title = { Text("Battle options") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            showBattleDialog = false
                            onContinue()
                        }
                    ) { Text("Start battle HOST (NFC)") }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            showBattleDialog = false
                            onContinue()
                        }
                    ) { Text("Start battle CLIENT (NFC)") }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            showBattleDialog = false
                            showBanCategoryDialog = true
                        }
                    ) { Text("Game settings (host)") }
                }
            },
            confirmButton = {
                Button(onClick = { showBattleDialog = false }) { Text("Close") }
            }
        )
    }

    if (showBanCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showBanCategoryDialog = false },
            title = { Text("Ban category (host only)") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    categories.forEach { category ->
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                bannedCategory = category
                                showBanCategoryDialog = false
                            }
                        ) { Text(category) }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showBanCategoryDialog = false }) { Text("Close") }
            }
        )
    }
}

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
