package com.skalipera.highfivequiz.ui.utility

enum class PayloadType {
    DRAGON_ID,
    QUESTIONS,
    ROUND_SCORE
}

data class GamePayload(
    val type: PayloadType,
    val data: String // This will hold the actual data as a String (or JSON string)
)