package com.skalipera.highfivequiz.ui.navigation

object AppDestinations {
    const val MAIN_MENU = "main_menu"
    const val DRAGON_COLLECTION = "dragon_collection"
    const val LOADOUT_BUILDER = "loadout_builder"
    const val BAN_CATEGORY = "ban_category"
    const val NFC_LOBBY = "nfc_lobby"
    const val NFC_LOBBY_ROUTE = "nfc_lobby/{role}/{playerName}"
    const val TRIVIA_ROUND = "trivia_round"
    const val WAITING = "waiting"
    const val DRAGON_ATTACK_SELECT = "dragon_attack_select"
    const val NFC_CLASH = "nfc_clash"
    const val ROUND_RESULT = "round_result"
    const val REWARDS = "rewards"

    fun nfcLobby(role: String, playerName: String): String = "$NFC_LOBBY/$role/$playerName"
}
