package com.waniaebro.core.utils

import com.waniaebro.core.data.source.local.entity.FilmEntity
import com.waniaebro.core.data.source.remote.response.DetailFilmResponse
import com.waniaebro.core.data.source.remote.response.ResultsItem
import com.waniaebro.core.domain.model.Film

object DataMapper {
    fun responseFilmsToDomain(films: List<ResultsItem>, favorite: List<FilmEntity>): List<Film> {
        val filmDomain = mutableListOf<Film>()
        films.map { film ->
            filmDomain.add(
                Film(
                    film.id,
                    film.voteAverage,
                    "",
                    "https://image.tmdb.org/t/p/w500${film.posterPath}",
                    film.title,
                    film.overview,
                    favorite.any {
                        it.id == film.id
                    }
                )
            )
        }
        return filmDomain
    }

    fun responseFilmToDomain(film: DetailFilmResponse, favorite: List<FilmEntity>): Film {
        var genre = ""
        film.genres.forEach {
            genre = if (genre.isNotEmpty()) "$genre, ${it.name}" else it.name
        }
        return Film(
            film.id,
            film.voteAverage,
            genre,
            "https://image.tmdb.org/t/p/w500${film.backdropPath}",
            film.title,
            film.overview,
            favorite.any {
                it.id == film.id
            }
        )
    }

    fun entityToDomain(films: List<FilmEntity>): List<Film> {
        val filmDomain = mutableListOf<Film>()
        films.map { film ->
            filmDomain.add(
                Film(
                    film.id,
                    film.rating,
                    film.genre,
                    film.photoUrl,
                    film.title,
                    film.overview,
                    true
                )
            )
        }
        return filmDomain
    }

    fun domainToEntity(film: Film): FilmEntity {
        return FilmEntity(
            film.id,
            film.rating,
            film.genre,
            film.photoUrl,
            film.title,
            film.overview
        )
    }
}