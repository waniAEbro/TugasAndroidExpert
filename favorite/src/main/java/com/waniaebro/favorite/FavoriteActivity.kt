package com.waniaebro.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.waniaebro.core.ui.FilmAdapter
import com.waniaebro.di.favoriteModule
import com.waniaebro.favorite.databinding.ActivityFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        favoriteViewModel.getFavorite().observe(this@FavoriteActivity) { result ->
            if (result.isEmpty()) {
                binding.notFoundTv.visibility = View.VISIBLE
            } else {
                binding.notFoundTv.visibility = View.GONE
                val adapter = FilmAdapter { _, _ ->
                }
                binding.rvContainer.adapter = adapter
                adapter.submitList(result)
            }
        }
    }
}