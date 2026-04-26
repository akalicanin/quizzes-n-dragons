package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    clickedForward : () -> Unit,
    clickedBackward : () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.DarkGray).padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        // row za naziv i stats
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            Text("DRAGON INFO", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(currentDragonID.toString(), modifier = Modifier.padding(horizontal = 20.dp), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Normal)
                Text(currentDragonName, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            Text(currentDragonType, modifier = Modifier.padding(vertical = 10.dp), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            Text(currentDragonDesc, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal)

        }

        // Row right for dragon image and forward/backward buttons
        Row(
            modifier = Modifier.weight(2f).fillMaxWidth().background(Color.DarkGray).padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            // GO BACK button
            Image(
                painter = painterResource(id = R.drawable.arrow_key_left), // TODO Replace with backwards sprite
                contentDescription = "Back button",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable{clickedBackward()}
            )
            // DRAGON image
            Image(
                painter = painterResource(id = R.drawable.filler_icon), // TODO Replace with dragon sprite
                contentDescription = "Dragon",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(200.dp)
            )
            // GO FORWARD button
            Image(
                painter = painterResource(id = R.drawable.arrow_key_right), // TODO Replace with forward sprite
                contentDescription = "Next button",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable{clickedForward()}
            )
        }
    }
}