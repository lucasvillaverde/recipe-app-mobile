package dev.lucasvillaverde.recipeapp.core.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun stringToMap(value: String): Map<String?, String?> {
        return Gson().fromJson(value, object : TypeToken<Map<String?, String?>>() {}.type)
    }

    @TypeConverter
    fun mapToString(value: Map<String?, String?>): String {
        return if (value.isNullOrEmpty()) "" else Gson().toJson(value)
    }
}