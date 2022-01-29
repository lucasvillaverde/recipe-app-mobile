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

/*        recipeDetailsViewModel.getRecipe(recipeId)

        recipeDetailsAdapter = RecipeDetailsPageAdapter(this, recipeId)
        binding.viewPager.adapter = recipeDetailsAdapter

        binding.ivFavoriteButton.setOnClickListener {
            recipeDetailsViewModel.toggleRecipeIsFavorite(recipeId)
        }

        recipeDetailsViewModel.pageState.observe(viewLifecycleOwner, {
            it.data?.let { data ->
                setTabLayout()
                updateUI(data)
            }
        })*/
    }

/*    private fun updateUI(recipe: dev.lucasvillaverde.recipes.domain.model.RecipeModel) {
        binding.mealDetailsCard.visibility = View.VISIBLE
        binding.ivFavoriteButton.setImageResource(
            when (recipe.isFavorite) {
                true -> R.drawable.ic_baseline_favorite_24
                false -> R.drawable.ic_baseline_favorite_border_24
            }
        )

        Picasso.get()
            .load(recipe.thumb)
            .into(binding.imgMeal)
        binding.imgMeal.visibility = View.VISIBLE

        // Actionbar
        actionBar?.title = recipe.name
        actionBar?.subtitle = recipe.category
    }

    private fun setTabLayout() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Instructions"
                1 -> tab.text = "Ingredients"
            }
        }.attach()
    }*/

    companion object {
        const val RECIPE_ID = "recipe_id"
    }
}