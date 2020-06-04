package dev.lucasvillaverde.recipeapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.data.remote.RetrofitService
import dev.lucasvillaverde.recipeapp.data.remote.responses.MealResponse
import dev.lucasvillaverde.recipeapp.data.remote.services.MealService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel.getRandomMeal().observe(this, Observer {
            txtMain.text = it.name
        })

        btnRefreshMeal.setOnClickListener(View.OnClickListener {
            mainViewModel.getRandomMeal()
        })
    }
}
