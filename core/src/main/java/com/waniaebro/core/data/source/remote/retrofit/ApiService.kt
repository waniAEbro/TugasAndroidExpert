package com.waniaebro.core.data.source.remote.retrofit

import com.waniaebro.core.data.source.remote.response.DetailFilmResponse
import com.waniaebro.core.data.source.remote.response.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("top_rated")
    suspend fun getAllFilm(@Header("Authorization") authorization: String): FilmResponse

    @GET("{id}")
    suspend fun getFilm(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): DetailFilmResponse
}