package dev.lucasvillaverde.favorite_recipes.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.common.base.presenter.BaseFragment
import dev.lucasvillaverde.common.base.presenter.NavDirection
import dev.lucasvillaverde.common.theme.RecipeAppTheme

@AndroidEntryPoint
@ExperimentalMaterialApi
class FavoriteRecipesFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            RecipeAppTheme {
                FavoriteRecipeScreen(
                    onFavoriteRecipeClick = { openRecipeDetails(it) },
                    onBackPressed = { requireActivity().onBackPressed() }
                )
            }
        }
    }

    private fun openRecipeDetails(id: Int) {
        val navDirection = NavDirection.FavoriteRecipesToRecipeDetails(
            bundleOf(
                Pair(
                    NavDirection.FavoriteRecipesToRecipeDetails.recipeIdArgName, id
                )
            )
        )

        navigator.navigateToDirection(navDirection)
    }
}