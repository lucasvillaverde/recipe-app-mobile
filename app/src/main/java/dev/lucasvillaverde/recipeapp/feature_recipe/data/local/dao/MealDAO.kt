package dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity

@Dao
interface MealDAO {

    @Query("SELECT * FROM meals ORDER BY name ASC")
    fun getMeals(): LiveData<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE id = :id")
    fun getMealById(id: Int): LiveData<MealEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meal: MealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(meals: List<MealEntity>)

    @Query("DELETE FROM meals")
    fun deleteAllMeals()
}