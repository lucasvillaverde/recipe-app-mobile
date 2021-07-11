package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details.IngredientsFragment
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details.InstructionsFragment

class RecipeDetailsPageAdapter(fragment: Fragment, private val recipeId: Int) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> InstructionsFragment.newInstance(recipeId)
            1 -> IngredientsFragment.newInstance(recipeId)
            else -> throw ClassNotFoundException("Unable to find fragment")
        }
}