package dev.lucasvillaverde.recipeapp.feature_recipe.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.databinding.FragmentInstructionsBinding

@AndroidEntryPoint
class InstructionsFragment : Fragment() {
    private lateinit var binding: FragmentInstructionsBinding
    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructionsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.intent?.let { intent ->
            val mealId = intent.getIntExtra("MEAL_ID", 0)
            mealDetailsViewModel.getMeal(mealId).observe(viewLifecycleOwner, {
                it?.let {
                    updateMealCard(it)
                }
            })
        }
    }

    private fun updateMealCard(meal: MealEntity) {
        binding.txtInstructions.text = meal.instructions
        binding.instructionsRoot.visibility = View.VISIBLE
    }
}