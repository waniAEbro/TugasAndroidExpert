package com.waniaebro.core.domain.repository

import com.waniaebro.core.data.source.remote.retrofit.ResultResponse
import com.waniaebro.core.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface IFilmRepository {
    fun getAllFilm(): Flow<ResultResponse<List<Film>>>

    fun getDetailFilm(id: Int): Flow<ResultResponse<Film>>

    fun getAllFavorite(): Flow<List<Film>>

    fun searchFilms(title: String): Flow<ResultResponse<List<Film>>>

    suspend fun setFavorite(film: Film)

    suspend fun deleteFavorite(film: Film)
}