package com.example.lazycolumngames.room

import com.example.lazycolumngames.model.Juego
class RepositoryRoom {
    val daoInterface = GameApplication.database.GameDao()
}