package dev.lucasvillaverde.recipes.presenter.recipe_list.adapter

import dev.lucasvillaverde.recipes.domain.model.RecipeModel

sealed class RecipeListItem(val data: RecipeModel? = null) {
    class Recipe(data: RecipeModel) : RecipeListItem(data)
    object Loader : RecipeListItem()
}
