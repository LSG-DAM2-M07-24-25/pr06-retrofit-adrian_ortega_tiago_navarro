package com.example.lazycolumngames.model

import androidx.room.PrimaryKey

data class Juego(
    val developer: String,
    val freetogame_profile_url: String,
    val game_url: String,
    val genre: String,
    val id: Int,
    val platform: String,
    val publisher: String,
    val release_date: String,
    val short_description: String,
    val thumbnail: String,
    val title: String,
    var is_favourite: Boolean = false
)
