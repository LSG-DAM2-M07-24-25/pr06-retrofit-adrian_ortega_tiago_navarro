package com.example.lazycolumngames.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lazycolumngames.api.Repository
import com.example.lazycolumngames.model.DatosAPI
import com.example.lazycolumngames.model.Juego
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel: ViewModel() {

    private val repository = Repository()

    private val _loading = MutableLiveData(true)
    val loading = _loading

    private val _allGames = MutableLiveData<List<Juego>>()
    val games = _allGames

    private val _game = MutableStateFlow<Juego?>(null)
    val game: StateFlow<Juego?> = _game

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite: StateFlow<Boolean> = _isFavourite

    fun getGames() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getGamesAPI()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    //_allGames.value = response.body()
                    _allGames.value = response.body()
                    _loading.value = false
                } else {
                    Log.e("Error: ", response.message())
                }
            }
        }
    }

    fun setGame(gameJson: String?) {
        gameJson?.let {
            val decodedJson = Uri.decode(it)
            _game.value = Gson().fromJson(decodedJson, Juego::class.java)
        }
    }

    fun toggleFavourite() {
        _game.value?.let { currentGame ->
            _game.update {
                currentGame.copy(is_favourite = !currentGame.is_favourite)
            }
        }
    }


}