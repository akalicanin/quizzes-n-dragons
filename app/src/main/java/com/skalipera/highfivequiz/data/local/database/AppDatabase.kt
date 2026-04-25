package com.skalipera.highfivequiz.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skalipera.highfivequiz.data.local.converter.AppTypeConverters
import com.skalipera.highfivequiz.data.local.dao.BattleLoadoutDao
import com.skalipera.highfivequiz.data.local.dao.DragonDao
import com.skalipera.highfivequiz.data.local.dao.InventoryDao
import com.skalipera.highfivequiz.data.local.dao.MatchHistoryDao
import com.skalipera.highfivequiz.data.local.dao.QuestionDao
import com.skalipera.highfivequiz.data.local.dao.UserStatsDao
import com.skalipera.highfivequiz.data.local.entity.BattleLoadoutEntity
import com.skalipera.highfivequiz.data.local.entity.DragonEntity
import com.skalipera.highfivequiz.data.local.entity.InventoryEntity
import com.skalipera.highfivequiz.data.local.entity.MatchHistoryEntity
import com.skalipera.highfivequiz.data.local.entity.QuestionEntity
import com.skalipera.highfivequiz.data.local.entity.RoundResultEntity
import com.skalipera.highfivequiz.data.local.entity.UserStatsEntity
import com.skalipera.highfivequiz.data.local.seed.DatabaseSeeder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserStatsEntity::class,
        DragonEntity::class,
        QuestionEntity::class,
        BattleLoadoutEntity::class,
        MatchHistoryEntity::class,
        RoundResultEntity::class,
        InventoryEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userStatsDao(): UserStatsDao
    abstract fun dragonDao(): DragonDao
    abstract fun questionDao(): QuestionDao
    abstract fun battleLoadoutDao(): BattleLoadoutDao
    abstract fun matchHistoryDao(): MatchHistoryDao
    abstract fun inventoryDao(): InventoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, appScope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "high-five-quiz.db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                        super.onCreate(db)
                        appScope.launch {
                            INSTANCE?.let { DatabaseSeeder.seed(it) }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
