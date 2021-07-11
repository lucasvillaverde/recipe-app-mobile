package dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.responses

import com.google.gson.annotations.SerializedName
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity

data class MealResponse (@SerializedName("meals") val mealList: List<MealEntity>)