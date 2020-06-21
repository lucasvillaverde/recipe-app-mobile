package dev.lucasvillaverde.recipeapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.lucasvillaverde.recipeapp.data.local.getDatabase
import dev.lucasvillaverde.recipeapp.data.repositories.MealRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val mealRepository = MealRepository(getDatabase(application))
    val isLoading = MutableLiveData(false)
    private val meals = mealRepository.meals

    fun getMeals() = meals

    fun getNewMeal() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                mealRepository.refreshMeals()
                isLoading.value = false
            } catch (ex: IOException){
                Log.d("GETNEWMEAL", "ERROR!")
            }
        }
    }

    fun deleteMeals() {
        viewModelScope.launch {
            isLoading.value = true
            mealRepository.deleteMeals()
            isLoading.value = false
        }
    }

    fun hasInternet(): Boolean {
        val cm = getApplication<Application>()
            .applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.isDefaultNetworkActive && cm.isActiveNetworkMetered
    }

}