package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skalipera.highfivequiz.R

@Composable
fun TopBar(
    nickname: String, rank: Int, coinAmount: Int,
    settingsClicked: () -> Unit,
    profileClicked: () -> Unit
           ) {

    val barHeight = 60

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    )
    {
        // SLIKA I IME
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
                    modifier = Modifier.clickable(onClick = {profileClicked()})
                )
            }

            Row()
            {

                Text(
                    nickname
                    // TODO: namesti font i slicno
                )

            }
        }

        // PARE, RANK I SETTINGS
        Row(
            modifier = Modifier.fillMaxWidth().height(barHeight.dp).background(Color.DarkGray),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            // pare
            Row()
            {
                Image(
                    painterResource(id = R.drawable.filler_icon),
                    contentDescription = null
                )
                Text(
                    coinAmount.toString()
                    // TODO: namesti font i slicno
                )
            }

            // rank
            Row()
            {
                Image(
                    painterResource(id = R.drawable.filler_icon),
                    contentDescription = null
                )
                Text(
                    rank.toString()
                    // TODO: namesti font i slicno
                )
            }

            // settings
            Row()
            {
                Button(
                    modifier = Modifier.size(49.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White),
                    onClick = {

                    }) {
//                    Text(text = "JB",
//                        fontSize = 13.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        style = TextStyle(
//                            lineHeightStyle = LineHeightStyle(
//                                alignment = LineHeightStyle.Alignment.Center,
//                                trim = LineHeightStyle.Trim.Both
//                            )
//                        )
//                    )
                    Image(
                        painterResource(id = R.drawable.filler_icon),
                        contentDescription = null
                    )
                }
            }
        }
    }
}