package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import dev.lucasvillaverde.recipes.domain.model.RecipeModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RecipeList(
    recipes: List<RecipeModel>,
    onRecipeClick: (recipeId: Int) -> Unit,
    onFavoriteClick: (recipeId: Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            key = { recipeModel -> recipeModel.id },
            items = recipes
        ) { recipe ->
            RecipeListItem(
                recipe,
                onRecipeClick,
                onFavoriteClick
            )

            if (recipe.hashCode() != recipes.last().hashCode()) {
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                Divider(
                    thickness = 2.dp
                )
            }
        }
    }
}

@Preview
@Composable
fun RecipeListPreview() {
    val recipe = RecipeModel(
        id = 0,
        tags = null,
        youtubeLink = null,
        name = "Test Recipe",
        category = "Dessert",
        region = "Test Region",
        instructions = "Test Instructions",
        thumb = null,
        ingredientsMeasures = mapOf(),
        isFavorite = false
    )
    RecipeList(
        recipes = listOf(
            recipe,
            recipe.copy(id = 1),
            recipe.copy(id = 2),
            recipe.copy(id = 3),
            recipe.copy(id = 4),
            recipe.copy(id = 5),
            recipe.copy(id = 6),
        ),
        onRecipeClick = {},
        onFavoriteClick = {}
    )
}