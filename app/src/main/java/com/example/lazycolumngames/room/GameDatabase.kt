package com.example.lazycolumngames.room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lazycolumngames.model.Juego

@Database(entities = (arrayOf(Juego::class)), version = 1)

abstract class GameDatabase: RoomDatabase() {
    abstract fun GameDao() : GameDao
}