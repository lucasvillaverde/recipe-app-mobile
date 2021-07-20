package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.base.presenter.MainActivity
import dev.lucasvillaverde.recipeapp.databinding.FragmentRecipeDetailsBinding
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details.adapter.RecipeDetailsPageAdapter

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailsBinding
    private lateinit var recipeDetailsAdapter: RecipeDetailsPageAdapter
    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(layoutInflater, container, false)
        (activity as MainActivity).supportActionBar?.show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId = requireArguments().getInt(MEAL_ID)
        recipeDetailsViewModel.getRecipe(recipeId)

        lifecycle.addObserver(binding.youtubePlayerView)

        recipeDetailsAdapter = RecipeDetailsPageAdapter(this, recipeId)
        binding.viewPager.adapter = recipeDetailsAdapter

        binding.ivFavoriteButton.setOnClickListener {
            recipeDetailsViewModel.toggleRecipeIsFavorite(recipeId)
        }

        recipeDetailsViewModel.pageState.observe(viewLifecycleOwner, {
            it.data?.let { data ->
                setTabLayout()
                loadMediaUI(data)
                updateUI(data)
            }
        })
    }

    private fun updateUI(recipe: RecipeModel) {
        binding.mealDetailsCard.visibility = View.VISIBLE
        binding.ivFavoriteButton.setImageResource(
            when (recipe.isFavorite) {
                true -> R.drawable.ic_baseline_favorite_24
                false -> R.drawable.ic_baseline_favorite_border_24
            }
        )

        // Actionbar
        (activity as MainActivity).supportActionBar?.title = recipe.name
        (activity as MainActivity).supportActionBar?.subtitle = recipe.category
    }

    private fun loadMediaUI(recipe: RecipeModel) {
        // TODO: 11/07/2021 - improve performance

        /* val youtubeVideoID = recipe.getYoutubeVideoID()

        if (youtubeVideoID != null && DeviceUtils.hasInternet(requireActivity().applicationContext)) {
            setupYoutubePlayerListener(youtubeVideoID)
            binding.imgMeal.visibility = View.GONE

            return
        }
        */

        Picasso.get()
            .load(recipe.thumb)
            .into(binding.imgMeal)
        binding.imgMeal.visibility = View.VISIBLE
    }

    private fun setTabLayout() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Instructions"
                1 -> tab.text = "Ingredients"
            }
        }.attach()
    }

    private fun setupYoutubePlayerListener(videoId: String) {
        binding.mediaLoader.visibility = View.VISIBLE
        binding.youtubePlayerView.addYouTubePlayerListener(object : YouTubePlayerListener {
            override fun onApiChange(youTubePlayer: YouTubePlayer) {
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
            }

            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer,
                playbackQuality: PlayerConstants.PlaybackQuality
            ) {
            }

            override fun onPlaybackRateChange(
                youTubePlayer: YouTubePlayer,
                playbackRate: PlayerConstants.PlaybackRate
            ) {
            }

            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0F)
                binding.mediaLoader.visibility = View.GONE
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
            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                youTubePlayer.pause()
            }

            override fun onVideoLoadedFraction(
                youTubePlayer: YouTubePlayer,
                loadedFraction: Float
            ) {
            }
        })
    }

    companion object {
        const val MEAL_ID = "meal_id"
    }
}