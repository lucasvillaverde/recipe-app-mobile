package dev.lucasvillaverde.recipeapp.data.remote.services

import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.data.remote.responses.MealResponse
import retrofit2.Call
import retrofit2.http.GET

interface MealService {

    @GET("random.php")
    fun getRandomMealData() : Call<MealResponse>

}