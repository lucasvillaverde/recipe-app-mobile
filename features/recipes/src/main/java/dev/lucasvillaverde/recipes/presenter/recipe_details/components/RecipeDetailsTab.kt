package dev.lucasvillaverde.recipes.presenter.recipe_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.lucasvillaverde.common.theme.RecipeAppTheme

@Composable
fun RecipeDetailsTab(
    recipeInstructions: String,
    recipeIngredientsMeasures: Map<String?, String?>
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        TabType.INSTRUCTIONS,
        TabType.INGREDIENTS
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TabRow(
            selectedTabIndex = selectedIndex
        ) {
            tabs.forEachIndexed { index, tabType ->
                Tab(
                    selected = index == selectedIndex,
                    onClick = { selectedIndex = index }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = tabType.name
                    )
                }
            }
        }

        RecipeDetailsTabContent(
            tabType = tabs[selectedIndex],
            recipeInstructions = recipeInstructions,
            recipeIngredientsMeasures = recipeIngredientsMeasures
        )
    }
}

@Preview
@Composable
fun RecipeDetailsTabPreview() {
    RecipeAppTheme {
        RecipeDetailsTab(
            recipeInstructions = "Test Instructions",
            recipeIngredientsMeasures = mapOf(
                "Salt" to "100g",
                "Sugar" to "130g"
            ),
        )
    }
}