package com.waniaebro.core.domain.model

data class Film(
    val id: Int,

    val rating: Double,

    val genre: String = "",

    val photoUrl: String,

    val title: String,

    val overview: String,

    val isFavorite: Boolean = false
)
