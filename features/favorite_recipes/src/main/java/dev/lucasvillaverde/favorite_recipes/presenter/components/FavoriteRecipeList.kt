package dev.lucasvillaverde.favorite_recipes.presenter.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe

@ExperimentalMaterialApi
@Composable
fun FavoriteRecipeList(
    favoriteRecipes: List<FavoriteRecipe>,
    onFavoriteRecipeClick: (id: Int) -> Unit,
    onRemoveFavoriteRecipeClick: (id: Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            key = { recipe -> recipe.id },
            items = favoriteRecipes
        ) { recipe ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) onRemoveFavoriteRecipeClick(recipe.id)
                    it != DismissValue.DismissedToStart
                }
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = {
                    FractionalThreshold(0.25f)
                },
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.Transparent
                            DismissValue.DismissedToEnd -> Color.Green
                            DismissValue.DismissedToStart -> Color.Red
                        }
                    )
                    val alignment = Alignment.CenterEnd
                    val icon = Icons.Default.Delete
                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                    )

                    if (dismissState.targetValue == DismissValue.Default)
                        return@SwipeToDismiss

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Localized description",
                            modifier = Modifier.scale(scale),
                            tint = Color.LightGray
                        )
                    }
                },
                dismissContent = {
                    FavoriteRecipeListItem(
                        favoriteRecipe = recipe,
                        onFavoriteRecipeClick = onFavoriteRecipeClick
                    )
                }
            )

            Spacer(Modifier.height(12.dp))

            if (recipe.hashCode() != favoriteRecipes.last().hashCode()) {
                Divider(color = Color(0xFFEBEBEB))
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun FavoriteRecipeListPreview() {
    FavoriteRecipeList(
        favoriteRecipes = listOf(
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
        ),
        onFavoriteRecipeClick = {},
        onRemoveFavoriteRecipeClick = {}
    )
}