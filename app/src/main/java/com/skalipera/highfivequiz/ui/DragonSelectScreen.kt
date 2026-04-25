package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skalipera.highfivequiz.viewmodel.GameViewModel

@Composable
fun DragonSelectScreen(
    dragons: List<GameViewModel.Dragon>,
    onDragonSelected: (GameViewModel.Dragon) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF1E1E1E)).padding(16.dp)
    ) {
        Text("CHOOSE YOUR DRAGON", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // This creates a grid with 3 columns
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dragons) { dragon ->
                // The individual item box
                Box(
                    modifier = Modifier
                        .aspectRatio(1f) // Forces it to be a perfect square
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.DarkGray)
                        .clickable { onDragonSelected(dragon) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = dragon.imageResId),
                        contentDescription = dragon.name,
                        modifier = Modifier.padding(8.dp).fillMaxSize()
                    )
                }
            }
        }
    }
}