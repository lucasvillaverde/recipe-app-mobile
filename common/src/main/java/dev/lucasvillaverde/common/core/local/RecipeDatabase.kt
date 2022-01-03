package dev.lucasvillaverde.common.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.lucasvillaverde.common.core.local.dao.FavoriteRecipesDao
import dev.lucasvillaverde.common.core.local.dao.RecipeDao
import dev.lucasvillaverde.common.core.local.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val favoriteRecipesDao: FavoriteRecipesDao
}