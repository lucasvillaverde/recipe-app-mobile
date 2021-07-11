package dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services

import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.responses.RecipeResponse
import retrofit2.http.GET

interface RecipeService {

    @GET("random.php")
    suspend fun getNewRecipe(): RecipeResponse
}