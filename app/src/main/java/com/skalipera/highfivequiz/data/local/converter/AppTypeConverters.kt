package com.skalipera.highfivequiz.data.local.converter

import androidx.room.TypeConverter
import com.skalipera.highfivequiz.core.CardType
import com.skalipera.highfivequiz.core.DragonType
import com.skalipera.highfivequiz.core.InventoryItemType
import com.skalipera.highfivequiz.core.MatchOutcome
import com.skalipera.highfivequiz.core.QuestionCategory
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class AppTypeConverters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun dragonTypeToString(value: DragonType?): String? = value?.name

    @TypeConverter
    fun stringToDragonType(value: String?): DragonType? = value?.let(DragonType::valueOf)

    @TypeConverter
    fun questionCategoryToString(value: QuestionCategory?): String? = value?.name

    @TypeConverter
    fun stringToQuestionCategory(value: String?): QuestionCategory? = value?.let(QuestionCategory::valueOf)

    @TypeConverter
    fun cardTypeToString(value: CardType?): String? = value?.name

    @TypeConverter
    fun stringToCardType(value: String?): CardType? = value?.let(CardType::valueOf)

    @TypeConverter
    fun inventoryItemTypeToString(value: InventoryItemType?): String? = value?.name

    @TypeConverter
    fun stringToInventoryItemType(value: String?): InventoryItemType? = value?.let(InventoryItemType::valueOf)

    @TypeConverter
    fun matchOutcomeToString(value: MatchOutcome?): String? = value?.name

    @TypeConverter
    fun stringToMatchOutcome(value: String?): MatchOutcome? = value?.let(MatchOutcome::valueOf)

    @TypeConverter
    fun stringListToJson(value: List<String>?): String = json.encodeToString(ListSerializer(String.serializer()), value.orEmpty())

    @TypeConverter
    fun jsonToStringList(value: String): List<String> = json.decodeFromString(ListSerializer(String.serializer()), value)

    @TypeConverter
    fun longListToJson(value: List<Long>?): String = json.encodeToString(ListSerializer(Long.serializer()), value.orEmpty())

    @TypeConverter
    fun jsonToLongList(value: String): List<Long> = json.decodeFromString(ListSerializer(Long.serializer()), value)
}
