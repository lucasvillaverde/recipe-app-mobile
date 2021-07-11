package dev.lucasvillaverde.recipeapp.feature_recipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao.MealDAO
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity

@Database(entities = [MealEntity::class], version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract val mealDAO: MealDAO
}