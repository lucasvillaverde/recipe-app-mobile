package dev.lucasvillaverde.recipe_app

import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe

val recipeMockList = listOf(
    FavoriteRecipe(
        id = 1,
        name = "Test Recipe",
        category = "Testing",
        photoUrl = ""
    ),
    FavoriteRecipe(
        id = 2,
        name = "Test Recipe 2",
        category = "Testing 2",
        photoUrl = ""
    ),
    FavoriteRecipe(
        id = 3,
        name = "Test Recipe 3",
        category = "Testing 3",
        photoUrl = ""
    )
)