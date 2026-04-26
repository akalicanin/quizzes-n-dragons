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
fun LoseScreen(
    rankWon: Int,
    goldWon: Int,
    onBackToMain: () -> Unit,
    onRematch: () -> Unit,
    hp: Int,
    isRematchRequested: Boolean = false,
    isOpponentRematchRequested: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "YOU LOSE!",
            color = Color.Red,
            fontSize = 48.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text ="♥ $hp",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Rewards Section
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Rank Points: $rankWon",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Gold Earned: +$goldWon",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(64.dp))

        // Buttons
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Rematch Button
            val buttonText = when {
                isRematchRequested && isOpponentRematchRequested -> "STARTING..."
                isRematchRequested -> "WAITING FOR OPPONENT..."
                isOpponentRematchRequested -> "OPPONENT WANTS REMATCH!"
                else -> "REMATCH"
            }

            Box(
                modifier = Modifier
                    .size(width = 250.dp, height = 60.dp)
                    .clip(CutCornerShape(12.dp))
                    .background(if (isRematchRequested) Color.Gray else Color(0xFF4CAF50))
                    .clickable(enabled = !isRematchRequested) { onRematch() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buttonText,
                    color = Color.White,
                    fontSize = if (isRematchRequested) 14.sp else 20.sp,
                    fontWeight = FontWeight.Black
                )
            }

            // Back to Main Button
            Box(
                modifier = Modifier
                    .size(width = 250.dp, height = 60.dp)
                    .clip(CutCornerShape(12.dp))
                    .background(Color(0xFFF44336))
                    .clickable { onBackToMain() },
                contentAlignment = Alignment.Center
            ) {
                Text("BACK TO MAIN", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}
