package dev.lucasvillaverde.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.viewmodels.MealDetailsViewModel
import kotlinx.android.synthetic.main.fragment_instructions.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MEAL_ID = "MEAL_ID"

/**
 * A simple [Fragment] subclass.
 * Use the [InstructionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class InstructionsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mealId = 0
    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getInt(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_instructions, container, false)
        mealDetailsViewModel.getMeal(mealId).observe(viewLifecycleOwner, Observer {
            it?.let {
                updateMealCard(it)
            }
        })
        return view
    }

    private fun updateMealCard(meal: MealEntity) {
        txtInstructions.text = meal.instructions
        instructionsRoot.visibility = View.VISIBLE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param mealId Parameter 1.
         * @return A new instance of fragment InstructionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(mealId: Int) =
            InstructionsFragment().apply {
                arguments = Bundle().apply {
                    putInt(MEAL_ID, mealId)
                }
            }
    }
}