package dev.lucasvillaverde.recipes.presenter.recipe_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RecipeDetailsTabContent(
    tabType: TabType,
    recipeInstructions: String,
    recipeIngredientsMeasures: Map<String?, String?>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        when (tabType) {
            TabType.INSTRUCTIONS -> {
                LazyColumn {
                    item {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = recipeInstructions
                        )
                    }
                }
            }
            TabType.INGREDIENTS -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.secondary),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1F)
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        text = "Ingredients"
                    )
                    Text(
                        modifier = Modifier
                            .weight(1F)
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        text = "Measures"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    var index = 0
                    items(recipeIngredientsMeasures.toList()) { pair ->
                        Row(
                            modifier = Modifier
                                .background(
                                    if (index % 2 == 0) Color.LightGray.copy(alpha = 0.3F)
                                    else Color.White
                                )
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                modifier = Modifier.weight(1F),
                                text = pair.first!!
                            )
                            Text(
                                modifier = Modifier.weight(1F),
                                text = pair.second!!
                            )
                        }

                        index += 1
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
        recipeInstructions = "Test Instructions",
        recipeIngredientsMeasures = mapOf(
            "Salt" to "100g",
            "Sugar" to "130g"
        ),
    )
}