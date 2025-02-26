package com.example.pr06_retrofit_adrian_tiago.viewmodel

import com.example.pr06_retrofit_adrian_tiago.api.Repository
import com.example.pr06_retrofit_adrian_tiago.model.DatosAPI
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pr06_retrofit_adrian_tiago.model.DatosAPIItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue


class MyViewModel: ViewModel() {

    private val repository = Repository()
    private val _loading = MutableLiveData(true)
    val loading = _loading
    private val _games = MutableLiveData<DatosAPI>()
    val games = _games
    private val _allGames = MutableLiveData<MutableList<DatosAPIItem>>()
    val allGames = _allGames
    private val _isFavourite = MutableLiveData(false)
    val isFavourite = _isFavourite
    private val _favourites = MutableLiveData<MutableList<DatosAPIItem>>()
    val favourites = _favourites

    fun getGames() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllGames()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _games.value = response.body()
                    repository.addGamesRoom(_games.value?.juegos ?: emptyList() )
                    _loading.value = false
                } else {
                    Log.e("Error :", response.message())
                }
            }
        }
    }

    fun getFavourites() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getFavourites()
            withContext(Dispatchers.Main) {
                favourites.value = response
                _loading.value = false
            }
        }
    }

    fun isFavourite(favouriteGame: DatosAPIItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isFavourite(favouriteGame.title)
            withContext(Dispatchers.Main) {
                _isFavourite.value = response
            }
        }
    }

    fun addFavourite(favouriteGame: DatosAPIItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.addFavourite(favouriteGame)
        }
    }

    fun removeFavourite(favouriteGame: DatosAPIItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.removeFavourite(favouriteGame)
        }
    }
}