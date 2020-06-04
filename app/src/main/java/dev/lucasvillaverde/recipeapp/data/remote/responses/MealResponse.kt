package dev.lucasvillaverde.recipeapp.data.remote.responses

import com.google.gson.annotations.SerializedName
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity

data class MealResponse (@SerializedName("meals") val mealList: List<MealEntity>)