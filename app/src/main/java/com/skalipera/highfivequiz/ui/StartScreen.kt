package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StartScreen(
    isHost: Boolean,
    isReady: Boolean,
    onReadyClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LOBBY",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // 1. READY Button
        val readyColor = if (isReady) Color(0xFF1B5E20) else Color(0xFF4CAF50)
        StartScreenButton(
            text = if (isReady) "READY!" else "READY",
            color = readyColor,
            enabled = !isReady,
            onClick = onReadyClicked
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2. GAME SETTINGS Button
        val settingsColor = if (isHost) Color(0xFF2196F3) else Color(0xFF1A1A1A)
        StartScreenButton(
            text = "GAME SETTINGS",
            color = settingsColor,
            enabled = isHost,
            onClick = onSettingsClicked
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. BACK Button
        StartScreenButton(
            text = "BACK TO MAIN",
            color = Color(0xFFF44336),
            onClick = onBackClicked
        )
    }
}

@Composable
fun StartScreenButton(
    text: String,
    color: Color,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 280.dp, height = 70.dp)
            .clip(CutCornerShape(16.dp))
            .background(color)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) Color.White else Color.DarkGray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black
        )
    }
}
