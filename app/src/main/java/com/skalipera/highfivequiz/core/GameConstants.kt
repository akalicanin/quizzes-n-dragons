package com.skalipera.highfivequiz.core

object GameConstants {
    const val PLAYER_BASE_HP = 150
    const val DRAGON_BASE_HP = 50
    const val CORRECT_ANSWER_BASE_ATTACK = 10
    const val QUESTION_TIMER_SECONDS = 10
    const val MAX_DRAGONS_PER_MATCH = 3
    const val MAX_SAME_TYPE_DRAGONS_PER_MATCH = 2
    const val QUESTIONS_PER_MATCH = 10
    const val QUESTIONS_PER_CATEGORY = 5
    const val ATTACK_MATCH_MULTIPLIER = 1.1
    const val DEFAULT_PROTOCOL_VERSION = 1
    const val SESSION_STALE_AFTER_MILLIS = 60_000L

    val WIN_GOLD_REWARD_RANGE: IntRange = 60..100
    val LOSS_GOLD_REWARD_RANGE: IntRange = 20..40
    val WIN_RANK_REWARD_RANGE: IntRange = 25..40
    val LOSS_RANK_REWARD_RANGE: IntRange = -15..-5
}
