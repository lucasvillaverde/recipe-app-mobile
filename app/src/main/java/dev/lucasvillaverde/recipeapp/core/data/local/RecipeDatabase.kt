package dev.lucasvillaverde.recipeapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.lucasvillaverde.recipeapp.core.data.local.model.RecipeEntity
import dev.lucasvillaverde.favorite_recipes.data.local.FavoriteRecipesDao
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.RecipeDao

@Database(entities = [RecipeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val favoriteRecipesDao: dev.lucasvillaverde.favorite_recipes.data.local.FavoriteRecipesDao
}