package com.skalipera.highfivequiz.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ProfileDialog(
    currentNickname: String,
    onDismiss: () -> Unit,
    onSaveName: (String) -> Unit // Pass the new name back to the game!
) {
    // We create a temporary state for the text field while they are typing.
    var tempName by remember { mutableStateOf(currentNickname) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF222222),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("PLAYER PROFILE", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(24.dp))

                // Profile Picture Placeholder
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Gray, shape = CircleShape)
                        .clickable { println("Later: Open Photo Gallery!") },
                    contentAlignment = Alignment.Center
                ) {
                    Text("TAP TO\nCHANGE", fontSize = 10.sp, color = Color.White, textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Text Input Field
                OutlinedTextField(
                    value = tempName,
                    onValueChange = { newText ->
                        // Limit name to 12 characters so it doesn't break your TopBar UI
                        if (newText.length <= 12) tempName = newText
                    },
                    label = { Text("Nickname", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.Green,
                        unfocusedBorderColor = Color.DarkGray
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            onSaveName(tempName) // Send new name up!
                            onDismiss()          // Close dialog
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text("Save", color = Color.Black)
                    }
                }
            }
        }
    }
}