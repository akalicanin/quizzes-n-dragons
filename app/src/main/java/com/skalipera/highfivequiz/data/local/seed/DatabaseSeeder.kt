package com.skalipera.highfivequiz.data.local.seed

import com.skalipera.highfivequiz.core.DragonType
import com.skalipera.highfivequiz.core.QuestionCategory
import com.skalipera.highfivequiz.data.local.database.AppDatabase
import com.skalipera.highfivequiz.data.local.entity.DragonEntity
import com.skalipera.highfivequiz.data.local.entity.QuestionEntity
import com.skalipera.highfivequiz.data.local.entity.UserStatsEntity

object DatabaseSeeder {

    suspend fun seed(database: AppDatabase) {
        if (database.userStatsDao().countAll() == 0) {
            database.userStatsDao().upsert(UserStatsEntity())
        }

        if (database.dragonDao().countAll() == 0) {
            database.dragonDao().upsertAll(
                listOf(
                    DragonEntity(name = "Pyro Scholar", type = DragonType.PHYSICS),
                    DragonEntity(name = "Byte Wyrm", type = DragonType.HACKER),
                    DragonEntity(name = "Chrono Drake", type = DragonType.HISTORY)
                )
            )
        }

        if (database.questionDao().countAll() == 0) {
            database.questionDao().upsertAll(defaultQuestions())
        }
    }

    private fun defaultQuestions(): List<QuestionEntity> = listOf(
        QuestionEntity(1, "What is H2O?", QuestionCategory.CHEMISTRY, listOf("Water", "Hydrogen", "Oxygen", "Salt"), 0),
        QuestionEntity(2, "2 + 2 = ?", QuestionCategory.PHYSICS, listOf("3", "4", "5", "6"), 1),
        QuestionEntity(3, "CPU stands for?", QuestionCategory.HACKER, listOf("Central Process Unit", "Central Processing Unit", "Computer Power Unit", "Core Processing Utility"), 1),
        QuestionEntity(4, "Who discovered penicillin?", QuestionCategory.BIO, listOf("Pasteur", "Newton", "Fleming", "Curie"), 2),
        QuestionEntity(5, "First man on the moon?", QuestionCategory.HISTORY, listOf("Yuri Gagarin", "Neil Armstrong", "Buzz Aldrin", "John Glenn"), 1),
        QuestionEntity(6, "Capital of Japan?", QuestionCategory.GEOGRAPHY, listOf("Seoul", "Beijing", "Tokyo", "Kyoto"), 2),
        QuestionEntity(7, "How many players in a football team?", QuestionCategory.SPORTS, listOf("9", "10", "11", "12"), 2),
        QuestionEntity(8, "Author of Hamlet?", QuestionCategory.CULTURE, listOf("Dante", "Shakespeare", "Goethe", "Homer"), 1),
        QuestionEntity(9, "Which planet is known as Red Planet?", QuestionCategory.GENERAL, listOf("Mars", "Venus", "Jupiter", "Mercury"), 0),
        QuestionEntity(10, "Acceleration due to gravity on Earth?", QuestionCategory.PHYSICS, listOf("8.9", "9.8", "10.8", "7.8"), 1),
        QuestionEntity(11, "What does RAM mean?", QuestionCategory.HACKER, listOf("Random Access Memory", "Read Access Module", "Rapid Access Memory", "Readable Active Memory"), 0),
        QuestionEntity(12, "DNA is found in?", QuestionCategory.BIO, listOf("Cell nucleus", "Blood plasma", "Bones", "Hair only"), 0)
    )
}
