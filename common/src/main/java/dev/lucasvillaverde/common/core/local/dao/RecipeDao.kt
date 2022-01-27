package dev.lucasvillaverde.common.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.lucasvillaverde.common.core.local.model.RecipeEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :name || '%'")
    fun getRecipesByName(name: String): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): RecipeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<RecipeEntity>)

    @Query("UPDATE recipes SET isFavorite = NOT isFavorite WHERE id = :id")
    fun toggleRecipeIsFavorite(id: Int)

    @Query("DELETE FROM recipes")
    fun deleteAllRecipes()
}