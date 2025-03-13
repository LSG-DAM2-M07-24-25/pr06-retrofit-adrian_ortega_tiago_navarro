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
import com.example.lazycolumngames.room.RepositoryRoom

class MyViewModel: ViewModel() {

    private val repository = Repository()

    private val repositoryRoom = RepositoryRoom()

    private val _loading = MutableLiveData(true)
    val loading = _loading

    private val _allGames = MutableLiveData<List<Juego>>()
    val games = _allGames

    private val _game = MutableStateFlow<Juego?>(null)
    val game: StateFlow<Juego?> = _game

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite: StateFlow<Boolean> = _isFavourite

    private val _isLiked = MutableLiveData<Boolean>(false)
    val isLiked: MutableLiveData<Boolean> = _isLiked

    private val _liked = MutableLiveData<MutableList<Juego>>()
    val liked = _liked

    private val _isLikingGame = MutableLiveData<Boolean>(false)
    val isLikingGame: MutableLiveData<Boolean> = _isLikingGame


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

    fun getFavourtie(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositoryRoom.getFavourite()
            withContext(Dispatchers.Main) {
                liked.value = response
                _loading.value = false
            }
        }
    }

    fun isLiked(game: Juego){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositoryRoom.isLiked(game.title)
            withContext(Dispatchers.Main){
                _isLiked.value = response
            }
        }
    }
    fun likeGame(game: Juego, onComplete: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            // Llama a la nueva lógica de likeGame para manejar la inserción/actualización
            repositoryRoom.likeGame(game)
            getFavourtie()  // Actualiza los juegos favoritos
            withContext(Dispatchers.Main) {
                isLiked.value = true
                onComplete()
            }
        }
    }

    fun dislikeGame(game: Juego, onComplete: () -> Unit ){
        CoroutineScope(Dispatchers.IO).launch {
            repositoryRoom.dislikeGame(game)
            getFavourtie()
            withContext(Dispatchers.Main) {
                isLiked.value = false
                onComplete()
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

    fun toggleIsLiking(){
        this._isLikingGame.value = this._isLikingGame.value!!.not()
    }


}