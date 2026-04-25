package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skalipera.highfivequiz.R
import com.skalipera.highfivequiz.viewmodel.GameViewModel
import com.skalipera.highfivequiz.viewmodel.GameViewModel.ScreenType

@Composable
fun BottomBar(currentScreen : GameViewModel.ScreenType,
              onButtonClicked: (GameViewModel.ScreenType) -> Unit
) {

    val barHeight = 100

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(barHeight.dp)
                .background(Color.DarkGray)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            // Dragons Icon
            val dragonsIcon = if (currentScreen == ScreenType.DRAGONS) {
                R.drawable.dragon_barn_active
            } else {
                R.drawable.dragon_barn
            }
            Image(
                painterResource(id = dragonsIcon),
                contentDescription = null,
                modifier = Modifier.size(82.dp).clickable(onClick = {onButtonClicked(GameViewModel.ScreenType.DRAGONS)})
            )

            // Home Icon
            val homeIcon = if (currentScreen == ScreenType.HOME) {
                R.drawable.home_button_active
            } else {
                R.drawable.home_button
            }
            Image(
                painterResource(id = homeIcon),
                contentDescription = null,
                modifier = Modifier.size(90.dp).clickable(onClick = {onButtonClicked(GameViewModel.ScreenType.HOME)})
            )

            // Shop Icon
            val shopIcon = if (currentScreen == ScreenType.SHOP) {
                R.drawable.shop_button_active
            } else {
                R.drawable.shop_button
            }
            Image(
                painterResource(id = shopIcon),
                contentDescription = null,
                modifier = Modifier.size(82.dp).clickable(onClick = {onButtonClicked(GameViewModel.ScreenType.SHOP)})
            )
        }
    }
}