package com.skalipera.highfivequiz.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
            painter = painterResource(id = R.drawable.cave_background), // Replace with dungeon/forest background
            contentDescription = "Ambient Background",
            contentScale = ContentScale.Crop, // Zooms the image to perfectly fill the box without stretching
            modifier = Modifier.fillMaxSize()
        )

        // main dragon
        Image(
            painter = painterResource(id = R.drawable.filler_dragon), // TODO Replace with your dragon sprite
            contentDescription = "Your Dragon",
            modifier = Modifier
                .align(Alignment.Center) // Put him right in the middle
                .size(200.dp) // Make him big!
                .graphicsLayer {
                    // Apply the breathing animation scale to the X and Y axes
                    scaleX = breathingScale
                    scaleY = breathingScale
                }
                .clickable { dragonClicked() }
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
            Box(
                modifier = Modifier
                    .size(width = 180.dp, height = 70.dp)
                    .clip(CutCornerShape(16.dp))
                    .background(Color(0xFF4CAF50))
                    .clickable { startMatching() },
                contentAlignment = Alignment.Center
            ) {
                Text("PLAY", color = Color.Black, fontSize = 28.sp, fontWeight = FontWeight.Black)
            }

            // dragon selection box
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CutCornerShape(12.dp))
                    .background(Color(0xFF333333))
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
                color = Color.Black,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}