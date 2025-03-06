package com.example.lazycolumngames.api

import com.example.lazycolumngames.model.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface APIInterface {

    @GET("games")
    //suspend fun getGamesAPI(): List<Juego>
    //suspend fun getGamesAPI(): Response<DatosAPI>
    suspend fun getGamesAPI(): Response<List<Juego>>

    companion object {
        val BASE_URL = "https://www.freetogame.com/api/"
        fun create(): APIInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(APIInterface::class.java)
        }
    }
}