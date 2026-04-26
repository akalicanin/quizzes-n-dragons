package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skalipera.highfivequiz.R

@Composable
fun TopBar(
    nickname: String,
    coinAmount: Int,
    settingsClicked: () -> Unit,
    profileClicked: () -> Unit
) {
    val barHeight = 60

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(barHeight.dp)
            .background(Color.Transparent)
            // padding keeps things from touching the very edge of the phone screen
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // LEFT SIDE: Profile Pic & Nickname
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.clickable { profileClicked() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Text(
                text = nickname,
                color = Color.White,
                fontSize = 18.sp
            )
        }

        // RIGHT SIDE: Coins & Settings
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Gap between coins and settings
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gold_icon),
                    contentDescription = "Coins",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = coinAmount.toString(),
                    color = Color.Yellow,
                    fontSize = 18.sp
                )
            }

            // Settings button
            Image(
                painter = painterResource(id = R.drawable.setting_icon), // Your settings icon
                contentDescription = "Settings",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { settingsClicked() }
            )
        }
    }
}