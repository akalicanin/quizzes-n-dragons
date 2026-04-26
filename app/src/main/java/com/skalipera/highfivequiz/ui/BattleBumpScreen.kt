package com.skalipera.highfivequiz.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import com.skalipera.highfivequiz.ui.utility.ShakeDetector

@Composable
fun BattleBumpScreen(onBump: () -> Unit) {

    ShakeDetector(onBumpDetected = { onBump() })

    // A gentle, tame floating animation for the decoration
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_anim"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Decorative floating "Dragon Claw" icon drawn with Canvas
        Box(modifier = Modifier.offset(y = floatOffset.dp)) {
            Canvas(modifier = Modifier.size(80.dp)) {
                val path = Path().apply {
                    moveTo(size.width * 0.2f, size.height * 0.1f)
                    quadraticBezierTo(size.width * 0.5f, size.height * 0.5f, size.width * 0.3f, size.height * 0.9f)

                    moveTo(size.width * 0.5f, size.height * 0.0f)
                    quadraticBezierTo(size.width * 0.8f, size.height * 0.5f, size.width * 0.6f, size.height * 1.0f)

                    moveTo(size.width * 0.8f, size.height * 0.2f)
                    quadraticBezierTo(size.width * 1.0f, size.height * 0.5f, size.width * 0.9f, size.height * 0.8f)
                }
                drawPath(
                    path = path,
                    color = Color(0xFFE57373), // Muted red/dragon color
                    style = Stroke(width = 8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Text enclosed in a neat decorative box
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color(0xFFE57373).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.2f))
                .padding(horizontal = 32.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Ready your Dragon", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Slash your phone through the air!", color = Color.LightGray, fontSize = 16.sp)
            }
        }
    }
}