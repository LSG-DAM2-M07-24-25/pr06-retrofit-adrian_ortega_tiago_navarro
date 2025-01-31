package com.example.pr06_retrofit_adrian_tiago.api

class Repository {

    val apiInterface = APIInterface.create()

    suspend fun getAllGames() = apiInterface.getGames()
}