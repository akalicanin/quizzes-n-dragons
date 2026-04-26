package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun DrawScreen(newRank : Int, onHomeClicked : () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text("DRAW!", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Normal)
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "RANK",
                color = Color.LightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Text(
                text = newRank.toString(),
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "+15 coins",
                color = Color.White,
                fontSize = 28.sp,
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

@Composable
fun LeaveButton(
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