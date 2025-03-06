package com.example.lazycolumngames.api

class Repository {

    val apiInterface = APIInterface.create()

    suspend fun getGamesAPI() = apiInterface.getGamesAPI()
}