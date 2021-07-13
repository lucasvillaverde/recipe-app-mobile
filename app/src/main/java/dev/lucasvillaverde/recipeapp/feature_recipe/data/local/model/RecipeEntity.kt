package dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel

@Entity(tableName = "meals")
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
    val ingredientsMeasures: Map<String?, String?>
)

fun RecipeEntity.toModel() = RecipeModel(
    id = this.id,
    name = this.name ?: "Unknown",
    category = this.category ?: "Unknown",
    thumb = this.thumb,
    region = this.region ?: "Unknown",
    instructions = this.instructions,
    tags = this.tags,
    youtubeLink = this.youtubeLink,
    ingredientsMeasures = this.ingredientsMeasures
)