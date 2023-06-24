package com.waniaebro.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("film")
data class FilmEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("rating")
    val rating: Double,

    @ColumnInfo("genre")
    val genre: String,

    @ColumnInfo("photo_url")
    val photoUrl: String,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("overview")
    val overview: String
)
