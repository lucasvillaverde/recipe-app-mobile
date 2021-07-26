package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.MainActivity
import dev.lucasvillaverde.recipeapp.databinding.FragmentRecipeDetailsBinding
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details.adapter.RecipeDetailsPageAdapter

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailsBinding
    private lateinit var recipeDetailsAdapter: RecipeDetailsPageAdapter
    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(layoutInflater, container, false)
        (activity as MainActivity).supportActionBar?.show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId = requireArguments().getInt(RECIPE_ID)
        recipeDetailsViewModel.getRecipe(recipeId)

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
        })
    }

    private fun updateUI(recipe: RecipeModel) {
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
        (activity as MainActivity).supportActionBar?.title = recipe.name
        (activity as MainActivity).supportActionBar?.subtitle = recipe.category
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
    }

    companion object {
        const val RECIPE_ID = "recipe_id"
    }
}