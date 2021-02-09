package dev.lucasvillaverde.recipeapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.databinding.ActivityMealDetailsBinding
import dev.lucasvillaverde.recipeapp.ui.adapters.MealDetailsPageAdapter
import dev.lucasvillaverde.recipeapp.utils.DeviceUtils.hasInternet
import dev.lucasvillaverde.recipeapp.viewmodels.MealDetailsViewModel

@AndroidEntryPoint
class MealDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailsBinding
    private val mealDetailsAdapter = MealDetailsPageAdapter(supportFragmentManager, lifecycle)
    private var mealId = 0;

    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = mealDetailsAdapter
        lifecycle.addObserver(binding.youtubePlayerView)

        mealId = intent.getIntExtra("MEAL_ID", 0)
        mealDetailsViewModel.getMeal(mealId).observe(this, {
            it?.let {
                refreshViewPager()
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
            binding.youtubePlayerView.visibility = View.VISIBLE
            binding.youtubePlayerView.addYouTubePlayerListener(youtubeListener(youtubeVideoID))
        } else {
            Picasso.get().load(meal.thumb).into(binding.imgMeal);
            binding.youtubePlayerView.visibility = View.GONE
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

    private fun refreshViewPager() {
        mealDetailsAdapter.clearFragments()
        mealDetailsAdapter.addFragment(InstructionsFragment.newInstance(mealId))
        mealDetailsAdapter.addFragment(IngredientsFragment.newInstance(mealId))
    }

    private fun youtubeListener(youtubeVideoID: String) = object : AbstractYouTubePlayerListener() {
        @Override
        override fun onReady(youTubePlayer: YouTubePlayer) {
            super.onReady(youTubePlayer)
            youTubePlayer.cueVideo(youtubeVideoID, 0F)
        }
    }

}