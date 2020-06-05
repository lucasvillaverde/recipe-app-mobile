package dev.lucasvillaverde.recipeapp.data.remote.services

import dev.lucasvillaverde.recipeapp.data.remote.responses.MealResponse
import retrofit2.http.GET

interface MealService {

    @GET("random.php")
    suspend fun getRandomMealData(): MealResponse

    @GET("random.php")
    suspend fun getMeals(): MealResponse

}