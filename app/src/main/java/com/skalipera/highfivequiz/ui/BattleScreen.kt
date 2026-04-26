package com.skalipera.highfivequiz.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BattleScreen() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    // Animates a float from 0 to 1 over 400ms. We use this to draw the slash line.
    val slashProgress by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "slash_progress"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        // Decoration: The diagonal slash line drawing itself
        Canvas(modifier = Modifier.fillMaxSize()) {
            val startX = size.width * 0.8f
            val startY = size.height * 0.2f
            val endX = size.width * 0.2f
            val endY = size.height * 0.8f

            // Calculate current end position based on animation progress
            val currentX = startX + (endX - startX) * slashProgress
            val currentY = startY + (endY - startY) * slashProgress

            drawLine(
                color = Color(0xFFFFB74D), // Soft Orange/Gold
                start = Offset(startX, startY),
                end = Offset(currentX, currentY),
                strokeWidth = 20f,
                cap = StrokeCap.Round
            )
        }

        // The text smoothly fading in over the slash
        Text(
            text = "SLASH!",
            color = Color.White,
            fontSize = 54.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.graphicsLayer { this.alpha = slashProgress }
        )
    }
}