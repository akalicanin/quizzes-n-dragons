package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WaitingForOpponentsScreen(roundScore: Int, hp: Int) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text ="♥ $hp",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text("Waiting for opponent...", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Normal)
        Text("This round, you won $roundScore coins!", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)

    }
}