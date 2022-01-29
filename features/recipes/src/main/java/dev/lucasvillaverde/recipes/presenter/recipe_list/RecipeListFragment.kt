package dev.lucasvillaverde.recipes.presenter.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.common.base.presenter.BaseFragment
import dev.lucasvillaverde.common.base.presenter.NavDirection
import dev.lucasvillaverde.common.theme.RecipeAppTheme
import dev.lucasvillaverde.recipes.databinding.FragmentRecipeListBinding
import dev.lucasvillaverde.recipes.presenter.recipe_list.components.RecipeListScreen

@AndroidEntryPoint
class RecipeListFragment : BaseFragment() {
    lateinit var binding: FragmentRecipeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        actionBar?.hide()
        binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecipeListComposable()
    }

    private fun setupRecipeListComposable() {
        binding.cvRoot.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                RecipeAppTheme {
                    RecipeListScreen(
                        onRecipeClick = { recipeId ->
                            navigateToRecipeDetails(recipeId)
                        },
                        onFavoriteScreenButtonClick = {
                            navigateToFavoriteScreen()
                        }
                    )
                }
            }
        }

    }

    private fun navigateToRecipeDetails(recipeId: Int) {
        navigator.navigateToDirection(
            NavDirection.RecipeListToRecipeDetails(
                bundleOf(
                    Pair(
                        NavDirection.RecipeListToRecipeDetails.recipeIdArgName,
                        recipeId
                    )
                )
            )
        )
    }

    private fun navigateToFavoriteScreen() {
        navigator.navigateToDirection(
            NavDirection.RecipeListToFavoriteRecipes()
        )
    }
}