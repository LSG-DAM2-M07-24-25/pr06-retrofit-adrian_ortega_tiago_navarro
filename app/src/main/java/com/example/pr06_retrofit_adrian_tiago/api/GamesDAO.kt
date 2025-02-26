package com.example.pr06_retrofit_adrian_tiago.api
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pr06_retrofit_adrian_tiago.model.DatosAPIItem

@Dao
interface GamesDAO {
    @Query("SELECT * FROM games")
    fun getAllGamesDAO (): MutableList<DatosAPIItem>

    @Query("SELECT * FROM games WHERE is_favourite = 1")
    fun getFavourites (): MutableList<DatosAPIItem>

    @Query("SELECT is_favourite FROM games WHERE title = :title")
    fun isFavourite (title: String): Boolean

    @Insert
    suspend fun addGamesRoom(list: List<DatosAPIItem>)

    @Insert
    fun addFavourite (favouriteGame: DatosAPIItem)

    @Delete
    fun removeFavourite (favouriteGame: DatosAPIItem)
}