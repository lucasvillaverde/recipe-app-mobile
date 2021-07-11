package dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.responses

import com.google.gson.annotations.SerializedName
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.RecipeEntity

data class RecipeResponse (@SerializedName("meals") val recipeList: List<RecipeEntity>)