package com.example.pr06_retrofit_adrian_tiago.room

import android.app.Application
import androidx.room.Room

class GamesApplication: Application() {
    companion object {
        lateinit var database: GamesDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, GamesDatabase::class.java, "GamesDatabase").build()
    }
}