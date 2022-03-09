package dev.lucasvillaverde.favorite_recipes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.favorite_recipes.presenter.FavoriteRecipeContent
import org.junit.Rule
import org.junit.Test

class FavoriteRecipeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowFavoriteRecipesOnScreen() {
        composeTestRule.setContent {
            FavoriteRecipeContent(
                favoriteRecipeState = BaseResource.Success(recipeMockList),
                onFavoriteRecipeClick = {},
                onRemoveFavoriteRecipe = {}
            )
        }

        composeTestRule.onNodeWithText("Test Recipe").assertIsDisplayed()
    }
}