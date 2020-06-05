package dev.lucasvillaverde.recipeapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity

@Dao
interface MealDAO {

    @Query("SELECT * FROM meals")
    fun getMeals(): LiveData<List<MealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meal: MealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(meals: List<MealEntity>)
}