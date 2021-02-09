package dev.lucasvillaverde.recipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.lucasvillaverde.recipeapp.data.local.dao.MealDAO
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity

@Database(entities = [MealEntity::class], version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract val mealDAO: MealDAO
}