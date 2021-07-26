package dev.lucasvillaverde.recipes.data.remote.services

import dev.lucasvillaverde.recipes.data.remote.model.RecipeListResponse
import retrofit2.http.GET

interface RecipeService {
    @GET("random.php")
    suspend fun getNewRecipe(): RecipeListResponse

}