package com.skalipera.highfivequiz.ui.navigation

import androidx.compose.runtime.Composable
import android.net.Uri
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skalipera.highfivequiz.data.nfc.NfcBattleRole
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
            MainMenuScreen(
                onOpenDragons = { navController.navigate(AppDestinations.DRAGON_COLLECTION) },
                onStartHostBattle = { playerName ->
                    navController.navigate(
                        AppDestinations.nfcLobby(NfcBattleRole.HOST.name, Uri.encode(playerName))
                    )
                },
                onStartClientBattle = { playerName ->
                    navController.navigate(
                        AppDestinations.nfcLobby(NfcBattleRole.CLIENT.name, Uri.encode(playerName))
                    )
                }
            )
        }
        composable(AppDestinations.DRAGON_COLLECTION) {
            DragonCollectionScreen { navController.navigate(AppDestinations.LOADOUT_BUILDER) }
        }
        composable(AppDestinations.LOADOUT_BUILDER) {
            LoadoutBuilderScreen { navController.navigate(AppDestinations.BAN_CATEGORY) }
        }
        composable(AppDestinations.BAN_CATEGORY) {
            BanCategoryScreen {
                navController.navigate(AppDestinations.nfcLobby(NfcBattleRole.HOST.name, "Player"))
            }
        }
        composable(
            route = AppDestinations.NFC_LOBBY_ROUTE,
            arguments = listOf(
                navArgument("role") { type = NavType.StringType },
                navArgument("playerName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rawRole = backStackEntry.arguments?.getString("role").orEmpty()
            val role = runCatching { NfcBattleRole.valueOf(rawRole) }.getOrDefault(NfcBattleRole.HOST)
            val playerName = backStackEntry.arguments?.getString("playerName").orEmpty()
            NfcLobbyScreen(
                role = role,
                playerName = playerName,
                onContinue = { navController.navigate(AppDestinations.TRIVIA_ROUND) }
            )
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
