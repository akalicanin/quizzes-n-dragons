package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skalipera.highfivequiz.R

@Composable
fun DragonsScreen(
    currentDragonName : String,
    currentDragonType : String,
    currentDragonDesc : String,
    currentDragonID : Int,
    currentDragonImage: Int,

    clickedForward : () -> Unit,
    clickedBackward : () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.dragon_barn_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Subtle dark overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            // row za naziv i stats
            Column(
                modifier = Modifier
                    .weight(1.2f) // Increased weight slightly to provide more room
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top // Anchor to top so positions are fixed
            )
            {
                Spacer(modifier = Modifier.height(24.dp)) // Fixed offset from the top
                Text(
                    "DRAGON INFO",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        currentDragonID.toString(),
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        currentDragonName,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    currentDragonType,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.TopCenter) {
                    Text(
                        currentDragonDesc,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Row right for dragon image and forward/backward buttons
            Row(
                modifier = Modifier
                    .weight(1.8f) // Adjusted weight to balance with the top section
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                // GO BACK button
                Image(
                    painter = painterResource(id = R.drawable.arrow_key_left),
                    contentDescription = "Back button",
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 5.dp)
                        .clickable { clickedBackward() }
                )
                // DRAGON image
                Image(
                    painter = painterResource(id = currentDragonImage),
                    contentDescription = "Dragon",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(220.dp)
                )
                // GO FORWARD button
                Image(
                    painter = painterResource(id = R.drawable.arrow_key_right),
                    contentDescription = "Next button",
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 5.dp)
                        .clickable { clickedForward() }
                )
            }
        }
    }
}