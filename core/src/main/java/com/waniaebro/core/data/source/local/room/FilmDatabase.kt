package com.waniaebro.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waniaebro.core.data.source.local.entity.FilmEntity

@Database(entities = [FilmEntity::class], version = 1, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}