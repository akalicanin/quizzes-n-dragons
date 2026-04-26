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
import com.skalipera.highfivequiz.ui.utility.ShakeDetector

@Composable
fun BattleBumpScreen(onBump : () -> Unit) {

    ShakeDetector(
        onBumpDetected = {
            onBump() // This tells the ViewModel the bump happened
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text("Get 'em!", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Slash your phone at your opponent!", color = Color.LightGray, fontSize = 18.sp)
    }
}