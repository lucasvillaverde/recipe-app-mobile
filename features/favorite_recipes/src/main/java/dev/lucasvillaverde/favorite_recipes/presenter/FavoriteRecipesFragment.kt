package dev.lucasvillaverde.favorite_recipes.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.base.presenter.BaseFragment
import dev.lucasvillaverde.common.base.presenter.NavDirection
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE
import dev.lucasvillaverde.favorite_recipes.presenter.adapter.FavoriteRecipesAdapter

@AndroidEntryPoint
class FavoriteRecipesFragment : BaseFragment() {
    lateinit var adapter: FavoriteRecipesAdapter
    // private val favoriteRecipesViewModel: FavoriteRecipesViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        actionBar?.show()
        setupUI()

        return ComposeView(requireContext()).apply {
            setContent {
                FavoriteRecipeScreen(onFavoriteRecipeClick = { openRecipeDetails(it) })
            }
        }
    }

    private fun setupUI() {
        actionBar?.title = "Favorite Recipes"
    }

/*    private fun setupFavoriteRecipesObserver() {
        favoriteRecipesViewModel.favoriteRecipes.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResource.Success -> adapter.submitList(it.data!!)
                is BaseResource.Error -> Toast.makeText(
                    requireActivity(),
                    COMMON_ERROR_MESSAGE,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }*/

/*    private fun removeRecipeFromFavorite(id: Int) {
        favoriteRecipesViewModel.removeFromFavorite(id)
    }*/

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