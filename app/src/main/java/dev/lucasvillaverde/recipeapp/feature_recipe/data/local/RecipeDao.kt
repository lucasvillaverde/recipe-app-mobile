package dev.lucasvillaverde.recipeapp.feature_recipe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.lucasvillaverde.recipeapp.core.data.local.model.RecipeEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY name ASC")
    fun getRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): RecipeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<RecipeEntity>)

    @Query("UPDATE recipes SET isFavorite = 1 WHERE id = :id")
    fun markRecipeAsFavorite(id: Int)

    @Query("DELETE FROM recipes")
    fun deleteAllRecipes()
}