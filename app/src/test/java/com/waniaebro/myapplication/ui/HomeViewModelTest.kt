package com.waniaebro.myapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.waniaebro.core.data.source.remote.retrofit.ResultResponse
import com.waniaebro.core.domain.model.Film
import com.waniaebro.core.domain.usecase.FilmUseCase
import com.waniaebro.myapplication.data.FilmDummy
import com.waniaebro.myapplication.home.HomeViewModel
import com.waniaebro.myapplication.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : KoinTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    private val homeViewModel: HomeViewModel by inject()
    private lateinit var mockFilmUseCase: FilmUseCase

    @Before
    fun before() {
        mockFilmUseCase = mock(FilmUseCase::class.java)
        startKoin {
            modules(
                listOf(
                    module(override = true) {
                        single { mockFilmUseCase }
                    },
                    module {
                        viewModel { HomeViewModel(get()) }
                    }
                )
            )
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `cek search bar kosong dan data semua film sukses`() = runTest {
        val expectedFilms = MutableStateFlow(FilmDummy.getFilms())
        `when`(mockFilmUseCase.getAllFilm()).thenReturn(expectedFilms)
        val observer = mock<Observer<ResultResponse<List<Film>>>>()
        homeViewModel.searchResult.observeForever(observer)
        this.launch {
            homeViewModel.titleSearch.value = ""
        }
        delay(1000)
        verify(mockFilmUseCase).getAllFilm()
        verify(observer).onChanged(expectedFilms.value)
    }

    @Test
    fun `cek search bar terisi dan data semua film sukses`() = runTest {
        val expectedFilms = MutableStateFlow(FilmDummy.searchFilms("1"))
        `when`(mockFilmUseCase.searchFilms("1")).thenReturn(expectedFilms)
        val observer = mock<Observer<ResultResponse<List<Film>>>>()
        homeViewModel.searchResult.observeForever(observer)
        this.launch {
            homeViewModel.titleSearch.value = "1"
        }
        delay(1000)
        verify(mockFilmUseCase).searchFilms("1")
        verify(observer).onChanged(expectedFilms.value)
    }
}