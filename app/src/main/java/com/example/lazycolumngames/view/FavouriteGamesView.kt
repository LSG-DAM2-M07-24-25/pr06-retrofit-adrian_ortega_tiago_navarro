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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val juegos: List<Juego> by myViewModel.games.observeAsState(emptyList()) // Lista de juegos desde el ViewModel

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
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre los ítems
    ) {
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
fun TabletGamesList(modifier: Modifier, myNavController: NavController, juegos: List<Juego>) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre los ítems
    ) {
        items(juegos) { juego ->
            GameItem(game = juego, isTablet = true) {
                // Navega a la vista de detalle con los datos del juego
                val gameJson = Uri.encode(Gson().toJson(juego))
                myNavController.navigate("DetailView/$gameJson")
            }
        }
    }
}

@Composable
fun GameItem(game: Juego, isTablet: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick), // Maneja el clic en el juego
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del juego
            GlideImage(
                model = game.thumbnail,
                contentDescription = "Game Thumbnail",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Información básica del juego
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = game.short_description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Botón de favorito o íconos adicionales según el dispositivo
            if (isTablet) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    tint = Color.Red
                )
            }
        }
    }
}
