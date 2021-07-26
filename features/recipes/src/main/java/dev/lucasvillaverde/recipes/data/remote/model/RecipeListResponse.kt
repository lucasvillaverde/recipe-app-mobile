package dev.lucasvillaverde.recipes.data.remote.model

import com.google.gson.annotations.SerializedName

data class RecipeListResponse(@SerializedName("meals") val recipeList: List<RecipeResponse>)