package com.example.lazycolumngames.room

import android.app.Application
import androidx.room.Room

class GameApplication {
    companion object {
        lateinit var database: GameDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, GameDatabase::class.java, "GameDatabase").build()
    }
}