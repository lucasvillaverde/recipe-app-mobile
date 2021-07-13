package dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services

import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.model.RecipeListResponse
import retrofit2.http.GET

interface RecipeService {
    @GET("random.php")
    suspend fun getNewRecipe(): RecipeListResponse
}