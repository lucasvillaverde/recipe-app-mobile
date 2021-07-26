package dev.lucasvillaverde.recipes.domain

import dev.lucasvillaverde.common.base.domain.BaseEntityMapper
import dev.lucasvillaverde.common.core.local.model.RecipeEntity
import dev.lucasvillaverde.recipes.domain.model.RecipeModel

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