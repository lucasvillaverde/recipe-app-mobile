package dev.lucasvillaverde.recipeapp.feature_recipe.presenter

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.databinding.ActivityMealDetailsBinding
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.adapters.MealDetailsPageAdapter
import dev.lucasvillaverde.recipeapp.utils.DeviceUtils.hasInternet

@AndroidEntryPoint
class MealDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailsBinding
    private val mealDetailsAdapter = MealDetailsPageAdapter(this)

    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = mealDetailsAdapter
        lifecycle.addObserver(binding.youtubePlayerView)

        val mealId = intent.getIntExtra("MEAL_ID", 0)
        mealDetailsViewModel.getMeal(mealId).observe(this, {
            it?.let {
                setupYoutubePlayerListener()
                setTabLayout()
                loadMediaUI(it)
                updateUI(it)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun updateUI(meal: MealEntity) {
        binding.txtMealTitle.text = meal.name
        binding.txtMealDescription.text = meal.category
        showCard(true)

        // Actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = meal.name
    }

    private fun showCard(status: Boolean) {
        if (status)
            binding.mealDetailsCard.visibility = View.VISIBLE
        else
            binding.mealDetailsCard.visibility = View.GONE
    }

    private fun loadMediaUI(meal: MealEntity) {
        binding.mediaLoader.visibility = View.VISIBLE

        val youtubeVideoID = meal.getYoutubeVideoID()
        if (youtubeVideoID != null && hasInternet(applicationContext)) {
            binding.imgMeal.visibility = View.GONE
        } else {
            Picasso.get().load(meal.thumb).into(binding.imgMeal);
            binding.imgMeal.visibility = View.VISIBLE
        }

        binding.mediaLoader.visibility = View.GONE
    }

    private fun setTabLayout() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Instructions"
                }
                1 -> {
                    tab.text = "Ingredients"
                }
            }
        }.attach()
    }

    fun setupYoutubePlayerListener() {
        binding.youtubePlayerView.addYouTubePlayerListener(youTubePlayerListener)
    }

    private val youTubePlayerListener = object : YouTubePlayerListener {
        override fun onApiChange(youTubePlayer: YouTubePlayer) {
            TODO("Not yet implemented")
        }

        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            TODO("Not yet implemented")
        }

        override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
            TODO("Not yet implemented")
        }

        override fun onPlaybackQualityChange(
            youTubePlayer: YouTubePlayer,
            playbackQuality: PlayerConstants.PlaybackQuality
        ) {
            TODO("Not yet implemented")
        }

        override fun onPlaybackRateChange(
            youTubePlayer: YouTubePlayer,
            playbackRate: PlayerConstants.PlaybackRate
        ) {
            TODO("Not yet implemented")
        }

        override fun onReady(youTubePlayer: YouTubePlayer) {
        }

        override fun onStateChange(
            youTubePlayer: YouTubePlayer,
            state: PlayerConstants.PlayerState
        ) {
            if (state != PlayerConstants.PlayerState.BUFFERING)
                binding.youtubePlayerView.let {
                    if (it.visibility != View.GONE)
                        return

                    it.visibility = View.VISIBLE
                }
        }

        override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
            TODO("Not yet implemented")
        }

        override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
            TODO("Not yet implemented")
        }

        override fun onVideoLoadedFraction(
            youTubePlayer: YouTubePlayer,
            loadedFraction: Float
        ) {
            TODO("Not yet implemented")
        }
    }

}