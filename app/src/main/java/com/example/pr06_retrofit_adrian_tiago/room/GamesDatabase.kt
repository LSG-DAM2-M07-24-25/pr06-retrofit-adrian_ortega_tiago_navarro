package com.example.pr06_retrofit_adrian_tiago.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pr06_retrofit_adrian_tiago.api.GamesDAO
import com.example.pr06_retrofit_adrian_tiago.model.DatosAPIItem

@Database(entities = arrayOf(DatosAPIItem::class), version = 1)
abstract class GamesDatabase: RoomDatabase() {
    abstract fun gamesDao(): GamesDAO
}