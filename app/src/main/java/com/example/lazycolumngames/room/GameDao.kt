package com.example.lazycolumngames.room
import androidx.room.Dao
import androidx.room. Delete
import androidx.room. Insert
import androidx.room. Query
import com.example.lazycolumngames.model.DatosAPI
import com.example.lazycolumngames.model.Juego

@Dao
interface GameDao {
    @Query("SELECT * FROM games WHERE is_favourite = 1")
    fun getFavourite() : MutableList<Juego>

    @Query("SELECT * FROM games WHERE title = :title ")
    fun findByTitle (title: String): MutableList<Juego?>

    @Query("SELECT is_favourite FROM games WHERE title = :title ")
    fun isFavourite (title: String): Boolean

    @Insert
    fun likeGame(game: Juego)

    @Delete
    fun dislikeGame(game: Juego)

    @Query("UPDATE games SET is_favourite = :isLiked WHERE title = :title ")
    fun updateLikedStatus(title: String, isLiked: Boolean)


}