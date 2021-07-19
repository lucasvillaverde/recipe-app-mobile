package dev.lucasvillaverde.recipeapp.feature_recipe.domain

import dev.lucasvillaverde.recipeapp.base.domain.BaseEntityMapper
import dev.lucasvillaverde.recipeapp.core.data.local.model.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel

object RecipeMapper : BaseEntityMapper<RecipeEntity, RecipeModel> {
    override fun mapFromEntity(entity: RecipeEntity): RecipeModel = RecipeModel(
        id = entity.id,
        name = entity.name ?: "Unknown",
        category = entity.category ?: "Unknown",
        thumb = entity.thumb,
        region = entity.region ?: "Unknown",
        instructions = entity.instructions,
        tags = entity.tags,
        youtubeLink = entity.youtubeLink,
        ingredientsMeasures = entity.ingredientsMeasures,
        isFavorite = entity.isFavorite
    )

    override fun mapToEntity(domainModel: RecipeModel): RecipeEntity {
        TODO("Not yet implemented")
    }
}