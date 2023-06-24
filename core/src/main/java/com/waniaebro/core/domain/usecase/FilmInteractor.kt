package com.waniaebro.core.domain.usecase

import com.waniaebro.core.data.source.remote.retrofit.ResultResponse
import com.waniaebro.core.domain.model.Film
import com.waniaebro.core.domain.repository.IFilmRepository
import kotlinx.coroutines.flow.Flow

class FilmInteractor(private val repository: IFilmRepository) : FilmUseCase {
    override fun getAllFilm(): Flow<ResultResponse<List<Film>>> = repository.getAllFilm()

    override fun getDetailFilm(id: Int): Flow<ResultResponse<Film>> = repository.getDetailFilm(id)

    override fun getAllFavorite(): Flow<List<Film>> = repository.getAllFavorite()

    override fun searchFilms(title: String): Flow<ResultResponse<List<Film>>> =
        repository.searchFilms(title)

    override suspend fun setFavorite(film: Film) = repository.setFavorite(film)

    override suspend fun deleteFavorite(film: Film) = repository.deleteFavorite(film)

}