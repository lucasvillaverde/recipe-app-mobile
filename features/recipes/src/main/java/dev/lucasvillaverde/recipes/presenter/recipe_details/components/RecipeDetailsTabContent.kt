package dev.lucasvillaverde.recipes.presenter.recipe_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.lucasvillaverde.recipes.domain.model.RecipeModel

@Composable
fun RecipeDetailsTabContent(
    tabType: TabType,
    recipeModel: RecipeModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (tabType) {
            TabType.INSTRUCTIONS -> {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = recipeModel.instructions ?: "No instructions."
                )
            }
            TabType.INGREDIENTS -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.secondary)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier.weight(1F),
                        fontWeight = FontWeight.Bold,
                        text = "Ingredients"
                    )
                    Text(
                        modifier = Modifier.weight(1F),
                        fontWeight = FontWeight.Bold,
                        text = "Measures"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(
                        modifier = Modifier.weight(1F)
                    ) {
                        recipeModel.ingredientsMeasures.keys.forEach {
                            it?.let { ingredient -> Text(ingredient) }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Column(
                        modifier = Modifier.weight(1F)
                    ) {
                        recipeModel.ingredientsMeasures.values.forEach {
                            it?.let { measure -> Text(measure) }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RecipeDetailsTabContentPreview() {
    RecipeDetailsTabContent(
        tabType = TabType.INGREDIENTS,
        recipeModel = RecipeModel(
            id = 0,
            tags = null,
            youtubeLink = null,
            name = "Test Recipe",
            category = "Dessert",
            region = "Test Region",
            instructions = "Test Instructions",
            thumb = null,
            ingredientsMeasures = mapOf(
                "Salt" to "100g",
                "Sugar" to "130g"
            ),
            isFavorite = false
        ),
    )
}