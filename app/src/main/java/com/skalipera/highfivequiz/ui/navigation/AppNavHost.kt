package com.skalipera.highfivequiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skalipera.highfivequiz.ui.screen.BanCategoryScreen
import com.skalipera.highfivequiz.ui.screen.DragonAttackSelectionScreen
import com.skalipera.highfivequiz.ui.screen.DragonCollectionScreen
import com.skalipera.highfivequiz.ui.screen.LoadoutBuilderScreen
import com.skalipera.highfivequiz.ui.screen.MainMenuScreen
import com.skalipera.highfivequiz.ui.screen.NfcClashScreen
import com.skalipera.highfivequiz.ui.screen.NfcLobbyScreen
import com.skalipera.highfivequiz.ui.screen.RewardsScreen
import com.skalipera.highfivequiz.ui.screen.RoundResultScreen
import com.skalipera.highfivequiz.ui.screen.TriviaRoundScreen
import com.skalipera.highfivequiz.ui.screen.WaitingScreen

@Composable
fun HighFiveQuizApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.MAIN_MENU,
        modifier = modifier
    ) {
        composable(AppDestinations.MAIN_MENU) {
            MainMenuScreen { navController.navigate(AppDestinations.DRAGON_COLLECTION) }
        }
        composable(AppDestinations.DRAGON_COLLECTION) {
            DragonCollectionScreen { navController.navigate(AppDestinations.LOADOUT_BUILDER) }
        }
        composable(AppDestinations.LOADOUT_BUILDER) {
            LoadoutBuilderScreen { navController.navigate(AppDestinations.BAN_CATEGORY) }
        }
        composable(AppDestinations.BAN_CATEGORY) {
            BanCategoryScreen { navController.navigate(AppDestinations.NFC_LOBBY) }
        }
        composable(AppDestinations.NFC_LOBBY) {
            NfcLobbyScreen { navController.navigate(AppDestinations.TRIVIA_ROUND) }
        }
        composable(AppDestinations.TRIVIA_ROUND) {
            TriviaRoundScreen { navController.navigate(AppDestinations.WAITING) }
        }
        composable(AppDestinations.WAITING) {
            WaitingScreen { navController.navigate(AppDestinations.DRAGON_ATTACK_SELECT) }
        }
        composable(AppDestinations.DRAGON_ATTACK_SELECT) {
            DragonAttackSelectionScreen { navController.navigate(AppDestinations.NFC_CLASH) }
        }
        composable(AppDestinations.NFC_CLASH) {
            NfcClashScreen { navController.navigate(AppDestinations.ROUND_RESULT) }
        }
        composable(AppDestinations.ROUND_RESULT) {
            RoundResultScreen { navController.navigate(AppDestinations.REWARDS) }
        }
        composable(AppDestinations.REWARDS) {
            RewardsScreen { navController.navigate(AppDestinations.MAIN_MENU) }
        }
    }
}
