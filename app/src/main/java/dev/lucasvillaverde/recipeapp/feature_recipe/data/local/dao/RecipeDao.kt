package dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.RecipeEntity

@Dao
interface RecipeDao {

    @Query("SELECT * FROM meals ORDER BY name ASC")
    fun getMeals(): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM meals WHERE id = :id")
    fun getMealById(id: Int): LiveData<RecipeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<RecipeEntity>)

    @Query("DELETE FROM meals")
    fun deleteAllMeals()
}