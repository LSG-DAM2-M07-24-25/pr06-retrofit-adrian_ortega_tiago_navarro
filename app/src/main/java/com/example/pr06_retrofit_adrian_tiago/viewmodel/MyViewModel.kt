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


class MyViewModel: ViewModel() {

    private val repository = Repository()
    private val _loading = MutableLiveData(true)
    val loading = _loading
    private val _games = MutableLiveData<DatosAPI>()
    val games = _games

    fun getGames() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllGames()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _games.value = response.body()
                    _loading.value = false
                } else {
                    Log.e("Error :", response.message())
                }
            }
        }
    }
}