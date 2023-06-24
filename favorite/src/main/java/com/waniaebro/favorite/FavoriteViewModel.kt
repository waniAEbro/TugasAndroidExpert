package com.waniaebro.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.waniaebro.core.domain.usecase.FilmUseCase

class FavoriteViewModel(private val useCase: FilmUseCase) : ViewModel() {
    fun getFavorite() = useCase.getAllFavorite().asLiveData()
}