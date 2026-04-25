package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skalipera.highfivequiz.R

const val barHeight = 100

@Composable
fun BottomBar(onButtonClicked: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth().height(barHeight.dp).background(Color.DarkGray),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Row()
            {
                Image(
                    painterResource(id = R.drawable.filler_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = {onButtonClicked("Dugme 1!")})
                )
            }

            Row()
            {
                Image(
                    painterResource(id = R.drawable.filler_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = {onButtonClicked("Dugme 2!")})
                )
            }
        }
        Image(
            painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.clickable(onClick = {onButtonClicked("Dugme PLAY!")})
        )
    }
}