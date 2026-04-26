package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoseScreen(newRank: Int, onHomeClicked : () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text("YOU LOST!", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Normal)
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO(Neka animacija za spustanje/dizanje ranka?)
            Text(
                text = "RANK",
                color = Color.LightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Text(
                text = newRank.toString(),
                color = Color.Red,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )
        }
        LeaveButton(
            text = "BACK HOME",
            color = Color(0xFFF44336),
            onClick = onHomeClicked
        )
    }
}