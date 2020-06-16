package dev.lucasvillaverde.recipeapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.ui.adapters.MealDetailsPageAdapter
import dev.lucasvillaverde.recipeapp.viewmodels.MealDetailsViewModel
import kotlinx.android.synthetic.main.activity_meal_details.*

class MealDetailsActivity : FragmentActivity() {

    private val mealDetailsAdapter = MealDetailsPageAdapter(supportFragmentManager, lifecycle)

    private val mealDetailsViewModel: MealDetailsViewModel by lazy {
        val application = requireNotNull(application) {
            "Application must not be null!"
        }
        ViewModelProvider(this).get(MealDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)
        viewPager.adapter = mealDetailsAdapter
        lifecycle.addObserver(youtubePlayerView)
        val mealId = intent.getIntExtra("MEAL_ID", 0)
        mealDetailsViewModel.getMealById(mealId).observe(this, Observer {
            refreshViewPager(it)
            setTabLayout()
            Log.d("GetCurrentItem", "${viewPager.currentItem}")
            loadMediaUI(it)
        })
    }

    private fun loadMediaUI(meal: MealEntity) {
        mediaLoader.visibility = View.VISIBLE

        val youtubeVideoID = meal.getYoutubeVideoID()
        if (youtubeVideoID != null && mealDetailsViewModel.hasInternet()) {
            imgMeal.visibility = View.GONE
            youtubePlayerView.visibility = View.VISIBLE
            youtubePlayerView.addYouTubePlayerListener(youtubeListener(youtubeVideoID))
        } else {
            Picasso.get().load(meal.thumb).into(imgMeal);
            youtubePlayerView.visibility = View.GONE
            imgMeal.visibility = View.VISIBLE
        }

        mediaLoader.visibility = View.GONE
    }

    private fun setTabLayout() {
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = "Instructions"}
                    1 -> { tab.text = "Ingredients"}
                }
            }).attach()
    }

    private fun refreshViewPager(meal: MealEntity) {
        mealDetailsAdapter.clearFragments()
        mealDetailsAdapter.addFragment(Instructions(meal))
        mealDetailsAdapter.addFragment(Ingredients(meal))
    }

    private fun youtubeListener(youtubeVideoID: String) = object: AbstractYouTubePlayerListener() {
        @Override
        override fun onReady(youTubePlayer: YouTubePlayer) {
            super.onReady(youTubePlayer)
            youTubePlayer.cueVideo(youtubeVideoID, 0F)
        }
    }

}