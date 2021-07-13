package dev.lucasvillaverde.recipeapp.feature_recipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao.RecipeDao
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
}