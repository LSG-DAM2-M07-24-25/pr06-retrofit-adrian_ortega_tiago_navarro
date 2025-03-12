package com.example.lazycolumngames.view

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lazycolumngames.model.DatosAPI
import com.example.lazycolumngames.model.Juego
import com.example.lazycolumngames.nav.Routes
import com.example.lazycolumngames.viewmodel.MyViewModel
import com.google.gson.Gson

@Composable
fun LazyColumnGames(modifier: Modifier, myNavController: NavController, myViewModel: MyViewModel) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    val showLoading: Boolean by myViewModel.loading.observeAsState(true)
    val juegos: List<Juego> by myViewModel.games.observeAsState(emptyList())

    myViewModel.getGames()

    if (showLoading) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = Color.Yellow)
        }
    } else {
        if (isTablet) {
            TabletGamesList(modifier, myNavController, juegos)
        } else {
            PhoneGamesList(modifier, myNavController, juegos)
        }
    }
}

@Composable
fun PhoneGamesList(modifier: Modifier, myNavController: NavController, juegos: List<Juego>) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(juegos) { juego ->
            GameItem(game = juego, isTablet = false) {
                val gameJson = Uri.encode(Gson().toJson(juego))
                myNavController.navigate("DetailView/$gameJson")
            }
        }
    }
}

@Composable
fun TabletGamesList(modifier: Modifier, myNavController: NavController, juegos: List<Juego>) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(juegos) { juego ->
            GameItem(game = juego, isTablet = true) {
                val gameJson = Uri.encode(Gson().toJson(juego))
                myNavController.navigate("DetailView/$gameJson")
            }
        }
    }
}
