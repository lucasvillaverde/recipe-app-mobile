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
import dev.lucasvillaverde.recipes.domain.model.RecipeModel

@Composable
fun RecipeDetailsTab(
    recipeModel: RecipeModel
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
            recipeModel = recipeModel
        )
    }
}

@Preview
@Composable
fun RecipeDetailsTabPreview() {
    RecipeAppTheme {
        RecipeDetailsTab(
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
        )
    }
}