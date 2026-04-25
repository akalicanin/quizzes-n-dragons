package com.skalipera.highfivequiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuizScreen(
    topic: String,
    questionText: String,
    answers: List<String>,
    currentQuestionIndex: Int,
    answerHistory: List<Boolean>,
    timeLeft: Int,
    onAnswerSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Progress circles (7)
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 36.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 0 until 7) {
                val circleColor = when {
                    i < currentQuestionIndex -> if (answerHistory[i]) Color.Green else Color.Red

                    i == currentQuestionIndex -> Color.White

                    else -> Color.DarkGray
                }

                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(circleColor)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Current topic
        Text(
            text = "TOPIC: ${topic.uppercase()}",
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Current question
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = questionText,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp
            )
        }


        // All answers
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Space between top and bottom row
        ) {
            // Top Row (Answers 0 and 1)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between left and right button
            ) {
                AnswerButton(
                    text = answers[0],
                    modifier = Modifier.weight(1f), // weight(1f) makes them take exactly 50% width!
                    onClick = { onAnswerSelected(answers[0]) }
                )
                AnswerButton(
                    text = answers[1],
                    modifier = Modifier.weight(1f),
                    onClick = { onAnswerSelected(answers[1]) }
                )
            }

            // Bottom Row (Answers 2 and 3)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnswerButton(
                    text = answers[2],
                    modifier = Modifier.weight(1f),
                    onClick = { onAnswerSelected(answers[2]) }
                )
                AnswerButton(
                    text = answers[3],
                    modifier = Modifier.weight(1f),
                    onClick = { onAnswerSelected(answers[3]) }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Timer
        val timerColor = if (timeLeft <= 3) Color.Red else Color.White

        Text(
            text = timeLeft.toString(),
            color = timerColor,
            fontSize = 56.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Answer button styling
@Composable
fun AnswerButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(80.dp), // Make buttons tall and easy to tap
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333))
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}