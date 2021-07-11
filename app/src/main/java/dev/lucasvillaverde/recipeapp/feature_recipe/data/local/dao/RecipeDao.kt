package dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM meals ORDER BY name ASC")
    fun getRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM meals WHERE id = :id")
    fun getRecipeById(id: Int): Flow<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<RecipeEntity>)

    @Query("DELETE FROM meals")
    fun deleteAllRecipes()
}