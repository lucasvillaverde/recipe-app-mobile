package dev.lucasvillaverde.recipeapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
class InstructionsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mealId = 0
    private val mealDetailsViewModel: MealDetailsViewModel by lazy {
        val application = requireNotNull(activity?.application) {
            "Application must not be null!"
        }
        ViewModelProvider(this).get(MealDetailsViewModel::class.java)
    }

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
        mealDetailsViewModel.getMeal(mealId).observe(viewLifecycleOwner, Observer { updateMealCard(it) })
        return inflater.inflate(R.layout.fragment_instructions, container, false)
    }

    private fun updateMealCard(meal: MealEntity) {
        txtInstructions.text = meal.instructions
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