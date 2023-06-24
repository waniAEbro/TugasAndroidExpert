package com.waniaebro.myapplication.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.waniaebro.core.data.source.remote.retrofit.ResultResponse
import com.waniaebro.myapplication.R
import com.waniaebro.myapplication.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.extras?.let { DetailActivityArgs.fromBundle(it).id } ?: -1

        if (id != -1) {
            detailViewModel.getFilm(id).observe(this@DetailActivity) { result ->
                when (result) {
                    ResultResponse.Empty -> {

                    }

                    is ResultResponse.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailActivity, result.message, Toast.LENGTH_LONG)
                            .show()
                    }

                    is ResultResponse.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResultResponse.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.apply {
                            titleTv.text = result.data.title
                            genreTv.text = result.data.genre
                            ratingTv.text = result.data.rating.toString()
                            overviewTv.text = result.data.overview
                            Glide.with(this@DetailActivity).load(result.data.photoUrl)
                                .into(binding.thumbnailImage)
                            if (result.data.isFavorite) {
                                favoriteButton.setImageResource(R.drawable.baseline_favorite_24)
                                favoriteButton.setOnClickListener {
                                    detailViewModel.deleteFavorite(result.data)
                                }
                            } else {
                                favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
                                favoriteButton.setOnClickListener {
                                    detailViewModel.setFavorite(result.data)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}