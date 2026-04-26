package com.skalipera.highfivequiz.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skalipera.highfivequiz.R

@Composable
fun AmbientView(
    rank: Int,
    selectedDragonImage: Int,
    isSearching: Boolean,
    isConnected: Boolean,
    dragonClicked: () -> Unit,
    startMatching: () -> Unit,
    openDragonSelection: () -> Unit,
    modifier: Modifier = Modifier
) {

    // for breathing animation
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")

    // We animate a float from 1.0 (normal size) to 1.05 (slightly bigger) and back
    val breathingScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dragon_scale"
    )

    Box(
        modifier = modifier.fillMaxSize() // Fill whatever space is left between Top/Bottom bars
    ) {

        // background Image
        Image(
            painter = painterResource(id = R.drawable.cave_background),
            contentDescription = "Ambient Background",
            contentScale = ContentScale.Crop, // Zooms the image without stretching
            modifier = Modifier.fillMaxSize()
        )

        // main dragon
        Image(
            painter = painterResource(id = selectedDragonImage),
            contentDescription = "Your Dragon",
            modifier = Modifier
                .align(Alignment.Center)
                .size(320.dp)
                .graphicsLayer {
                    // Apply the breathing animation scale to the X and Y axes
                    scaleX = breathingScale
                    scaleY = breathingScale
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // This removes the fade/ripple effect
                ) {
                    dragonClicked()
                }
        )

        // Play button
//        Image(
//            painter = painterResource(id = R.drawable.filler_icon), // TODO Replace with play icon
//            contentDescription = "Play button",
//            modifier = Modifier
//                .align(Alignment.BottomCenter) // Put him right in the middle
//                .padding(vertical = 10.dp)
//                .clickable { startMatching() }
//        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // play button
            val buttonText = when {
                isConnected -> "OPPONENT FOUND!"
                isSearching -> "LOOKING FOR PLAYERS..."
                else -> "PLAY"
            }
            val buttonColor = when {
                isConnected -> Color(0xFF2196F3) // Blue for success
                isSearching -> Color(0xFFFF9800) // Orange for searching
                else -> Color(0xFF4CAF50) // Green for idle
            }
            val fontSize = if (isSearching) 14.sp else 24.sp

            Box(
                modifier = Modifier
                    .size(width = 180.dp, height = 70.dp)
                    .clip(CutCornerShape(16.dp))
                    .background(buttonColor)
                    .clickable(enabled = !isSearching && !isConnected) { startMatching() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buttonText,
                    color = Color.White,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            // dragon selection box
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CutCornerShape(12.dp))
                    .background(Color(0xFFF5F198).copy(alpha = 0.6f))
                    .clickable { openDragonSelection() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = selectedDragonImage),
                    contentDescription = "Selected Dragon",
                    modifier = Modifier.padding(8.dp).fillMaxSize()
                )
            }
        }

        // rank HUD
        Column(
            modifier = Modifier
                .align(Alignment.TopStart) // Float this in the top left corner of the ambient view
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Center the text within this column
        ) {
            Text(
                text = "RANK",
                color = Color.LightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp // Adds a cool game-like spacing between letters
            )
            Text(
                text = rank.toString(),
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}