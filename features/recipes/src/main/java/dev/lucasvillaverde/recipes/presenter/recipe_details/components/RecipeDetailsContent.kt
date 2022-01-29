package dev.lucasvillaverde.recipes.presenter.recipe_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.lucasvillaverde.common.theme.RecipeAppTheme
import dev.lucasvillaverde.recipes.R
import dev.lucasvillaverde.recipes.domain.model.RecipeModel

@Composable
fun RecipeDetailsContent(
    recipeModel: RecipeModel,
    onFavoriteRecipe: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = recipeModel.thumb ?: R.drawable.ic_find_your_meal,
                contentScale = ContentScale.Crop,
                contentDescription = "Meal Image",
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.size(80.dp)
                    )
                }
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Button(
                    modifier = Modifier.size(40.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                    contentPadding = PaddingValues(4.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(Color.LightGray.copy(alpha = 0.7F)),
                    onClick = { onFavoriteRecipe(recipeModel.id) }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (recipeModel.isFavorite) R.drawable.ic_baseline_favorite_24
                            else R.drawable.ic_baseline_favorite_border_24
                        ),
                        tint = Color.Red,
                        contentDescription = "Favorite Recipe"
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

        }

        RecipeDetailsTab(
            recipeInstructions = recipeModel.instructions!!,
            recipeIngredientsMeasures = recipeModel.ingredientsMeasures
        )
    }
}

@Preview
@Composable
fun RecipeDetailsContentPreview() {
    RecipeAppTheme {
        RecipeDetailsContent(
            recipeModel = RecipeModel(
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
            ),
            onFavoriteRecipe = {}
        )
    }
}