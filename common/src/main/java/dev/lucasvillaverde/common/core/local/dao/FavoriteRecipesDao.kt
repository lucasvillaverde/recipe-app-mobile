package dev.lucasvillaverde.common.core.local.dao

import androidx.room.Dao
import androidx.room.Query
import dev.lucasvillaverde.common.core.local.model.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRecipesDao {
    @Query("SELECT * FROM recipes WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    @Query("UPDATE recipes SET isFavorite = 0 WHERE id = :id")
    fun removeRecipeFromFavorite(id: Int)
}