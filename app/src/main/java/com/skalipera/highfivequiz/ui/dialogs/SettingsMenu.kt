package com.skalipera.highfivequiz.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit // Function to close the dialog
) {
    // Local state just for these sliders.
    // Later, you'll save these to Android's SharedPreferences or DataStore so they persist!
    var musicVolume by remember { mutableStateOf(0.7f) } // 0.0 to 1.0
    var sfxVolume by remember { mutableStateOf(1.0f) }

    // The Dialog component creates the floating window and darkened background
    Dialog(onDismissRequest = { onDismiss() }) {
        // A Surface or Card gives the dialog its shape and background color
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF222222), // Dark game menu color
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("SETTINGS", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(24.dp))

                // Music Slider
                Text("Music Volume: ${(musicVolume * 100).toInt()}%", color = Color.LightGray)
                Slider(
                    value = musicVolume,
                    onValueChange = { musicVolume = it }, // 'it' is the new slider value
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFAEE683),
                        activeTrackColor = Color(0xFFAEE683)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // SFX Slider
                Text("SFX Volume: ${(sfxVolume * 100).toInt()}%", color = Color.LightGray)
                Slider(
                    value = sfxVolume,
                    onValueChange = { sfxVolume = it },
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFAEE683),
                        activeTrackColor = Color(0xFFAEE683)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text("Close", color = Color.White)
                }
            }
        }
    }
}
