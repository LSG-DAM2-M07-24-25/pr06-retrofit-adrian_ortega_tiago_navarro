package com.example.lazycolumngames.view

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.lazycolumngames.model.DatosAPI
import com.example.lazycolumngames.model.Juego
import com.example.lazycolumngames.nav.Routes
import com.example.lazycolumngames.viewmodel.MyViewModel
import com.google.gson.Gson

@Composable
fun LazyColumnGames(modifier: Modifier, myNavController: NavController, myViewModel: MyViewModel) {

    val showLoading: Boolean by myViewModel.loading.observeAsState(true)
    val juegos: List<Juego> by myViewModel.games.observeAsState(emptyList())

    myViewModel.getGames()

    if (showLoading) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = Color.Yellow
            )
        }
    } else {
        LazyColumn {
            items(juegos) { juego ->
                GameItem(game = juego) {
                    val gameJson = Uri.encode(Gson().toJson(juego))
                    myNavController.navigate("DetailView/$gameJson")
                }
            }
        }
    }
}