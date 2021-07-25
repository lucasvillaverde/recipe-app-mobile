package dev.lucasvillaverde.favorite_recipes.domain

import dev.lucasvillaverde.common.base.domain.BaseEntityMapper
import dev.lucasvillaverde.common.core.local.model.RecipeEntity
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe

object FavoriteRecipeMapper : BaseEntityMapper<RecipeEntity, FavoriteRecipe> {
    override fun mapFromEntity(entity: RecipeEntity): FavoriteRecipe = FavoriteRecipe(
        id = entity.id,
        name = entity.name ?: "Unknown",
        category = entity.category ?: "Unknown",
        photoUrl = entity.thumb ?: "Unknown"
    )

    override fun mapToEntity(domainModel: FavoriteRecipe): RecipeEntity {
        TODO("Not yet implemented")
    }
}