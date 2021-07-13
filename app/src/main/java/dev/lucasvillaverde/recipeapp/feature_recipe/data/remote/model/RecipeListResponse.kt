package dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.model

import com.google.gson.annotations.SerializedName

data class RecipeListResponse(@SerializedName("meals") val recipeList: List<RecipeResponse>)