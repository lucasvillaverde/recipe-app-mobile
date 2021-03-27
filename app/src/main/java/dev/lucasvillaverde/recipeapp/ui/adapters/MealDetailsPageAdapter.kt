package dev.lucasvillaverde.recipeapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.lucasvillaverde.recipeapp.ui.IngredientsFragment
import dev.lucasvillaverde.recipeapp.ui.InstructionsFragment

class MealDetailsPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> InstructionsFragment()
            1 -> IngredientsFragment()
            else -> InstructionsFragment()
        }
}