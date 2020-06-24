package dev.lucasvillaverde.recipeapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.lucasvillaverde.recipeapp.data.repositories.MealRepository

class MealDetailsViewModel @ViewModelInject constructor(
    @ApplicationContext private val appContext: Context,
    private val mealRepository: MealRepository
) : ViewModel() {

    fun hasInternet(): Boolean {
        val cm = appContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.isDefaultNetworkActive && cm.isActiveNetworkMetered
    }

    fun getMeal(id: Int) = mealRepository.getMealById(id)

}