package dev.lucasvillaverde.recipes.presenter.recipe_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.common.base.presenter.BaseFragment
import dev.lucasvillaverde.common.theme.RecipeAppTheme
import dev.lucasvillaverde.recipes.databinding.FragmentRecipeDetailsBinding
import dev.lucasvillaverde.recipes.presenter.recipe_details.components.RecipeDetailsScreen

@AndroidEntryPoint
class RecipeDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentRecipeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(layoutInflater, container, false)
        actionBar?.show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId = requireArguments().getInt(RECIPE_ID)
        binding.cvRecipeDetailsRoot.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                RecipeAppTheme {
                    RecipeDetailsScreen(recipeId = recipeId)
                }
            }
        }
    }

    companion object {
        const val RECIPE_ID = "recipe_id"
    }
}