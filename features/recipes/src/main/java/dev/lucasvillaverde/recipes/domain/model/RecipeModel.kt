package dev.lucasvillaverde.recipes.domain.model

data class RecipeModel(
    val id: Int,
    val name: String,
    val category: String,
    val thumb: String?,
    val region: String,
    val instructions: String?,
    val tags: String?,
    val youtubeLink: String?,
    val ingredientsMeasures: Map<String?, String?>,
    val isFavorite: Boolean
)
