package com.example.lazycolumngames.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lazycolumngames.model.Juego
import com.example.lazycolumngames.nav.Routes
import com.example.lazycolumngames.viewmodel.MyViewModel
import com.google.gson.Gson

@Composable
fun FavouriteGamesView(modifier: Modifier, myNavController: NavController, myViewModel: MyViewModel) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600 // Determina si el dispositivo es una tablet
    val showLoading: Boolean by myViewModel.loading.observeAsState(true) // Estado de carga
    val juegos: MutableList<Juego> by myViewModel.liked.observeAsState(mutableListOf()) // Lista de juegos desde el ViewModel

    myViewModel.getGames() // Obtiene los juegos

    if (showLoading) {
        // Muestra un CircularProgressIndicator mientras los juegos se están cargando
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = Color.Yellow)
        }
    } else {
        // Muestra la lista de juegos dependiendo si el dispositivo es una tablet o un teléfono
        if (isTablet) {
            TabletFavourite(modifier, myNavController, juegos)
        } else {
            PhoneFavourite(modifier, myNavController, juegos)
        }
    }
}

@Composable
fun PhoneFavourite(modifier: Modifier, myNavController: NavController, juegos: MutableList<Juego>) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre los ítems
    ) {
        item{
            Button(onClick = { myNavController.navigate("LazyColumnGames") }) {
                Text(text = "Volver")
            }
        }
        items(juegos) { juego ->
            GameItem(game = juego, isTablet = false) {
                // Navega a la vista de detalle con los datos del juego
                val gameJson = Uri.encode(Gson().toJson(juego))
                myNavController.navigate("DetailView/$gameJson")
            }
        }
    }
}

@Composable
fun TabletFavourite(modifier: Modifier, myNavController: NavController, juegos: MutableList<Juego>) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre los ítems
    ) {
        item{
            Button(onClick = { myNavController.navigate("LazyColumnGames") }) {
                Text(text = "Volver")
            }
        }
        items(juegos) { juego ->
            GameItem(game = juego, isTablet = true) {
                // Navega a la vista de detalle con los datos del juego
                val gameJson = Uri.encode(Gson().toJson(juego))
                myNavController.navigate("DetailView/$gameJson")
            }
        }
    }
}

