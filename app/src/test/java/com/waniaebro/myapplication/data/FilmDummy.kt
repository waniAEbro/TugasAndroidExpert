package com.waniaebro.myapplication.data

import com.waniaebro.core.data.source.remote.retrofit.ResultResponse
import com.waniaebro.core.domain.model.Film

object FilmDummy {
    fun searchFilms(title: String): ResultResponse<List<Film>> {
        val films = mutableListOf<Film>()
        for (i in 0..10) {
            films.add(
                Film(
                    i,
                    i.toDouble(),
                    "$i genre",
                    "$i url",
                    "$i title",
                    "$i overview",
                    false
                )
            )
        }
        return ResultResponse.Success(films.filter { it.title.contains(title) })
    }

    fun getFilms(): ResultResponse<List<Film>> {
        val films = mutableListOf<Film>()
        for (i in 0..10) {
            films.add(
                Film(
                    i,
                    i.toDouble(),
                    "$i genre",
                    "$i url",
                    "$i title",
                    "$i overview",
                    false
                )
            )
        }
        return ResultResponse.Success(films)
    }
}