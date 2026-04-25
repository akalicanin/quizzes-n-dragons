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
            modifier = Modifier.fillMaxWidth().height(barHeight.dp).background(Color.DarkGray).padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Row()
            {

                var dragonsIcon = if (currentScreen == ScreenType.DRAGONS) {
                    R.drawable.dragon_barn_active // TODO: ACTIVE icon
                } else {
                    R.drawable.dragon_barn // INACTIVE / regular icon
                }

                Image(
                    painterResource(id = dragonsIcon),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = {onButtonClicked(GameViewModel.ScreenType.DRAGONS)})
                )
            }

            Row()
            {

                var shopIcon = if (currentScreen == ScreenType.SHOP) {
                    R.drawable.shop_button_active // TODO: ACTIVE icon
                } else {
                    R.drawable.shop_button // INACTIVE / regular icon
                }

                Image(
                    painterResource(id = shopIcon),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = {onButtonClicked(GameViewModel.ScreenType.SHOP)})
                )
            }
        }

        var homeIcon = if (currentScreen == ScreenType.HOME) {
            R.drawable.home_button_active // TODO: ACTIVE icon
        } else {
            R.drawable.home_button // INACTIVE / regular icon
        }

        Image(
            painterResource(id = homeIcon),
            contentDescription = null,
            modifier = Modifier.clickable(onClick = {onButtonClicked(GameViewModel.ScreenType.HOME)})
        )
    }
}