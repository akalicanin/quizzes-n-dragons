package com.skalipera.highfivequiz

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "player_stats")

class PlayerStatsManager(private val context: Context) {

    // define keys for data
    companion object {
        val NICKNAME_KEY = stringPreferencesKey("nickname")
        val RANK_KEY = intPreferencesKey("rank")
        val COINS_KEY = intPreferencesKey("coins")
    }

    // returns a Flow so it's asynchronous
    val playerNicknameFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[NICKNAME_KEY] ?: "Barcelona" // Default value
    }

    val playerRankFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[RANK_KEY] ?: 0 // Default value
    }

    val playerCoinsFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[COINS_KEY] ?: 0 // Default value
    }

    suspend fun saveNickname(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NICKNAME_KEY] = name
        }
    }

    suspend fun saveRank(rank: Int) {
        context.dataStore.edit { preferences ->
            preferences[RANK_KEY] = rank
        }
    }

    suspend fun saveCoins(coins: Int) {
        context.dataStore.edit { preferences ->
            preferences[COINS_KEY] = coins
        }
    }
}