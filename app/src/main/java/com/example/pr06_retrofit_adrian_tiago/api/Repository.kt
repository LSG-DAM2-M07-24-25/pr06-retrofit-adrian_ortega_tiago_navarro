package com.example.pr06_retrofit_adrian_tiago.api

import com.example.pr06_retrofit_adrian_tiago.model.DatosAPIItem
import com.example.pr06_retrofit_adrian_tiago.room.GamesApplication

class Repository {

    val apiInterface = APIInterface.create()
    val daoInterface = GamesApplication.database.gamesDao()

    suspend fun getAllGames() = apiInterface.getGames()

    suspend fun addGamesRoom(list: List<DatosAPIItem>) = daoInterface.addGamesRoom(list)
    suspend fun getFavourites(): MutableList<DatosAPIItem> = daoInterface.getFavourites()
    suspend fun isFavourite(title: String): Boolean = daoInterface.isFavourite(title)
    suspend fun addFavourite(favouriteGame: DatosAPIItem) = daoInterface.addFavourite(favouriteGame)
    suspend fun removeFavourite(favouriteGame: DatosAPIItem) = daoInterface.removeFavourite(favouriteGame)

}