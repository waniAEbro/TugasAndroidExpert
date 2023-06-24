package com.waniaebro.core.data

import com.waniaebro.core.BuildConfig
import com.waniaebro.core.data.source.local.room.FilmDao
import com.waniaebro.core.data.source.remote.retrofit.ApiService
import com.waniaebro.core.data.source.remote.retrofit.ResultResponse
import com.waniaebro.core.domain.model.Film
import com.waniaebro.core.domain.repository.IFilmRepository
import com.waniaebro.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class FilmRepository(
    private val database: FilmDao,
    private val service: ApiService
) : IFilmRepository {
    override fun getAllFilm(): Flow<ResultResponse<List<Film>>> = flow {
        try {
            emit(ResultResponse.Loading)
            val response =
                service.getAllFilm("Bearer ${BuildConfig.KEY}")
            database.getAllFilm().collect {
                emit(ResultResponse.Success(DataMapper.responseFilmsToDomain(response.results, it)))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getDetailFilm(id: Int): Flow<ResultResponse<Film>> = flow {
        try {
            emit(ResultResponse.Loading)
            val response = service.getFilm(
                id,
                "Bearer ${BuildConfig.KEY}"
            )
            database.getAllFilm().collect {
                emit(ResultResponse.Success(DataMapper.responseFilmToDomain(response, it)))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllFavorite(): Flow<List<Film>> = database.getAllFilm().map {
        DataMapper.entityToDomain(it)
    }

    override fun searchFilms(title: String) = flow {
        try {
            emit(ResultResponse.Loading)
            val response =
                service.getAllFilm("Bearer ${BuildConfig.KEY}")
            database.getAllFilm().collect {
                val films = DataMapper.responseFilmsToDomain(response.results.filter { item ->
                    item.title.lowercase().contains(title.lowercase())
                }, it)
                emit(ResultResponse.Success(films))
                if (films.isEmpty()) {
                    emit(ResultResponse.Empty)
                }
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun setFavorite(film: Film) {
        val films = DataMapper.domainToEntity(film)
        database.insertFilm(films)
    }

    override suspend fun deleteFavorite(film: Film) {
        val films = DataMapper.domainToEntity(film)
        database.deleteFilm(films)
    }
}