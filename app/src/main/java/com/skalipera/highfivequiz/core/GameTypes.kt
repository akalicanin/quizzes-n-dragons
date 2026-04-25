package com.skalipera.highfivequiz.core

enum class DragonType {
    PHYSICS,
    HACKER,
    CHEMISTRY,
    BIO,
    HISTORY,
    GEOGRAPHY,
    SPORTS,
    CULTURE,
    GENERAL
}

enum class QuestionCategory {
    PHYSICS,
    HACKER,
    CHEMISTRY,
    BIO,
    HISTORY,
    GEOGRAPHY,
    SPORTS,
    CULTURE,
    GENERAL
}

enum class CardType {
    ATTACK_BOOST,
    ARMOR_BOOST,
    TIMER_BOOST,
    HEALING,
    NONE
}

enum class InventoryItemType {
    CARD,
    COSTUME
}

enum class MatchRole {
    HOST,
    GUEST
}

enum class MatchPhase {
    SETUP,
    TAP_1_SYNC,
    TRIVIA,
    WAITING,
    DRAGON_SELECT,
    TAP_2_CLASH,
    ROUND_RESOLVE,
    MATCH_RESOLVE
}

enum class MatchOutcome {
    WIN,
    LOSS,
    DRAW,
    SURRENDER
}
