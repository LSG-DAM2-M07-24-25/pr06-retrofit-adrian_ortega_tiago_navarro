package com.example.lazycolumngames.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Juego(
    val developer: String,
    val freetogame_profile_url: String,
    val game_url: String,
    val genre: String,
    @PrimaryKey val id: Int,
    val platform: String,
    val publisher: String,
    val release_date: String,
    val short_description: String,
    val thumbnail: String,
    val title: String,
    @ColumnInfo(name = "is_favourite") var is_favourite: Boolean = false
)
