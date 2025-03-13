package com.example.lazycolumngames.room

import com.example.lazycolumngames.model.Juego
class RepositoryRoom {
    val daoInterface = GameApplication.database.GameDao()

    suspend fun getFavourite(): MutableList<Juego> = daoInterface.getFavourite()
    suspend fun findByName(game: Juego) = daoInterface.findByTitle(game.title)
    suspend fun isLiked(title: String) = daoInterface.isFavourite(title)
    suspend fun likeGame(game: Juego) = daoInterface.likeGame(game)
    suspend fun dislikeGame(game: Juego) = daoInterface.dislikeGame(game)
    suspend fun updateLikeStatus(title: String, isLiked: Boolean) = daoInterface.updateLikedStatus(title, isLiked)
}