package dev.lucasvillaverde.recipeapp.data.remote

import dev.lucasvillaverde.recipeapp.utils.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    val retrofitService = Retrofit
        .Builder()
        .baseUrl(AppConstants.API.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}