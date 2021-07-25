package dev.lucasvillaverde.common.core.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    val category: String?,
    val region: String?,
    val instructions: String?,
    val thumb: String?,
    val tags: String?,
    val youtubeLink: String?,
    val ingredientsMeasures: Map<String?, String?>,
    val isFavorite: Boolean
)