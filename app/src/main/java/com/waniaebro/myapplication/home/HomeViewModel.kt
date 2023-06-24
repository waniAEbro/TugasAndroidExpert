package com.waniaebro.myapplication.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.waniaebro.core.domain.usecase.FilmUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.mapLatest

class HomeViewModel(private val useCase: FilmUseCase) : ViewModel() {
    val titleSearch = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResult = titleSearch.debounce(500).distinctUntilChanged()
        .mapLatest {
            if (it.isEmpty()) {
                useCase.getAllFilm()
            } else {
                useCase.searchFilms(it)
            }
        }.flattenMerge()
        .asLiveData()
}