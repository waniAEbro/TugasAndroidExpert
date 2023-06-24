package com.waniaebro.myapplication.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.waniaebro.core.domain.model.Film
import com.waniaebro.core.domain.usecase.FilmUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val useCase: FilmUseCase) : ViewModel() {
    fun setFavorite(film: Film) {
        viewModelScope.launch {
            useCase.setFavorite(film)
        }
    }

    fun deleteFavorite(film: Film) {
        viewModelScope.launch {
            useCase.deleteFavorite(film)
        }
    }

    fun getFilm(id: Int) = useCase.getDetailFilm(id).asLiveData()
}