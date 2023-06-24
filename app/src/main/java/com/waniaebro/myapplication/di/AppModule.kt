package com.waniaebro.myapplication.di

import com.waniaebro.core.domain.usecase.FilmInteractor
import com.waniaebro.core.domain.usecase.FilmUseCase
import com.waniaebro.myapplication.detail.DetailViewModel
import com.waniaebro.myapplication.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val usecaseModule = module {
    factory<FilmUseCase> {
        FilmInteractor(get())
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}