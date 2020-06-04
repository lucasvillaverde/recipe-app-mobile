package dev.lucasvillaverde.recipeapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.data.remote.RetrofitService
import dev.lucasvillaverde.recipeapp.data.remote.responses.MealResponse
import dev.lucasvillaverde.recipeapp.data.remote.services.MealService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealRepository {

    private val meal = MutableLiveData<MealEntity>()
    private val mealService = RetrofitService.retrofitService.create(MealService::class.java)

    fun getRandomMeal() : LiveData<MealEntity> {
        val randomMealCall = mealService.getRandomMealData()
        randomMealCall.enqueue(object: Callback<MealResponse> {
            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful){
                    response.body()?.mealList?.let {
                        meal.value = it[0]
                    }
                }
            }

        })

        return meal
    }

}