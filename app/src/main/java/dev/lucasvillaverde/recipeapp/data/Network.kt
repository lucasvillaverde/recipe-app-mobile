package dev.lucasvillaverde.recipeapp.data

import dev.lucasvillaverde.recipeapp.data.remote.services.MealService
import dev.lucasvillaverde.recipeapp.utils.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    private val retrofitInstance = Retrofit
        .Builder()
        .baseUrl(AppConstants.API.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mealService = retrofitInstance.create(MealService::class.java)

}