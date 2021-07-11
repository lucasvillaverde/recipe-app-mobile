package dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services

import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.responses.MealResponse
import retrofit2.http.GET

interface MealService {

    @GET("random.php")
    suspend fun getMeals(): MealResponse
}