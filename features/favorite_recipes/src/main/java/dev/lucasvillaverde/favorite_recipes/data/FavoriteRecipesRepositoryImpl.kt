package dev.lucasvillaverde.favorite_recipes.data

import dev.lucasvillaverde.common.core.local.dao.FavoriteRecipesDao
import dev.lucasvillaverde.common.core.local.model.RecipeEntity
import dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FavoriteRecipesRepositoryImpl(private val favoriteRecipesDao: FavoriteRecipesDao) :
    FavoriteRecipesRepository {
    override fun getFavoriteRecipes(): Flow<List<RecipeEntity>> =
        favoriteRecipesDao.getFavoriteRecipes().flowOn(Dispatchers.IO)

    override suspend fun removeRecipeFromFavorite(id: Int) = withContext(Dispatchers.IO) {
        favoriteRecipesDao.removeRecipeFromFavorite(id)
    }
}