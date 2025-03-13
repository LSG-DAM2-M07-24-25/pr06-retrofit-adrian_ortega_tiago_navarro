package com.example.lazycolumngames.view

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lazycolumngames.model.Juego
import com.example.lazycolumngames.nav.Routes
import com.example.lazycolumngames.viewmodel.MyViewModel
import okhttp3.internal.wait

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailView(gameJson: String?, myViewModel: MyViewModel) {
    LaunchedEffect(gameJson) {
        myViewModel.setGame(gameJson)
    }

    val game by myViewModel.game.collectAsState()
    val games: List<Juego> by myViewModel.games.observeAsState(emptyList())
    val gameRoom = games.find { it.title == gameJson }
    myViewModel.getFavourtie()
    val favourites: MutableList<Juego> by myViewModel.liked.observeAsState(mutableListOf())

    val isLikingGame by myViewModel.isLikingGame.observeAsState(false)
    val configuration = LocalConfiguration.current

    LaunchedEffect(gameRoom) {
        gameRoom?.let {
            myViewModel.isLiked(it)
        }
    }

    when {
        configuration.screenWidthDp < 600 -> {
            PhoneDetailView(game, myViewModel)
        }
        else -> {
            TabletDetailView(game, myViewModel)
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhoneDetailView(game: Juego?, myViewModel: MyViewModel) {
    val isLiked by myViewModel.isLiked.observeAsState(false)
    val isLikingGame by myViewModel.isLikingGame.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, bottom = 24.dp, start = 32.dp, end = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = game?.title ?: "Detalles",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            game?.let { currentGame ->
                // IconButton para darle like o dislike
                IconButton(onClick = {
                    // Cambiar el estado de "liking"
                    myViewModel.toggleIsLiking()
                    val gameToUpdate = currentGame.copy(is_favourite = !currentGame.is_favourite)

                    // Dependiendo si ya está marcado como "liked", hacer like o dislike
                    if (!isLiked) {
                        myViewModel.likeGame(gameToUpdate) {
                            myViewModel.toggleIsLiking() // Resetear el estado de "liking" después de la operación
                        }
                    } else {
                        myViewModel.dislikeGame(gameToUpdate) {
                            myViewModel.toggleIsLiking() // Resetear el estado de "liking" después de la operación
                        }
                    }
                }) {
                    // Aquí cambiamos el ícono dependiendo si está "liked"
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        contentDescription = "Favourite",
                        tint = if (isLiked) Color.Red else Color.Gray
                    )
                }

                // Mostrar un indicador de carga mientras se está "liking" o "disliking"

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = game?.thumbnail ?: "Imagen no disponible",
                contentDescription = "Game Thumbnail",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(1000.dp)
            )
        }

        game?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.Start
            ) {
                InfoRow("Género:", it.genre)
                InfoRow("Plataforma:", it.platform)
                InfoRow("Desarrollador:", it.developer)
                InfoRow("Publisher:", it.publisher)
                InfoRow("Lanzamiento:", it.release_date)

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Descripción:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = it.short_description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Yellow)
        }
    }
}



@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun GameDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TabletDetailView(game: Juego?, myViewModel: MyViewModel) {
    // Variables para manejar el estado de "liked" y "isLikingGame"
    val isLiked by myViewModel.isLiked.observeAsState(false)
    val isLikingGame by myViewModel.isLikingGame.observeAsState(false)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Imagen del juego a la izquierda
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = game?.thumbnail ?: "Imagen no disponible",
                contentDescription = "Game Thumbnail",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(500.dp)
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        // Detalles del juego a la derecha
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            // Título y botón de "like"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = game?.title ?: "Detalles",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )

                // Aquí se maneja el "like" o "dislike"
                game?.let { currentGame ->
                    IconButton(onClick = {
                        // Cambiar el estado de "liking"
                        myViewModel.toggleIsLiking()
                        val gameToUpdate = currentGame.copy(is_favourite = !currentGame.is_favourite)

                        // Dependiendo de si el juego ya está "liked", añadir o quitar
                        if (!isLiked) {
                            myViewModel.likeGame(gameToUpdate) {
                                myViewModel.toggleIsLiking() // Resetear el estado de "liking" después de la operación
                            }
                        } else {
                            myViewModel.dislikeGame(gameToUpdate) {
                                myViewModel.toggleIsLiking() // Resetear el estado de "liking" después de la operación
                            }
                        }
                    }) {
                        // Cambiar el ícono de favorito según el estado
                        Icon(
                            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                            contentDescription = "Favourite",
                            tint = if (isLiked) Color.Red else Color.Gray
                        )
                    }

                    // Mostrar un indicador de carga mientras se está procesando el like/dislike
                    if (isLikingGame) {
                        CircularProgressIndicator(color = Color.Yellow)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Información adicional sobre el juego
            game?.let {
                InfoRow(label = "Género:", value = it.genre)
                Spacer(Modifier.height(15.dp))

                InfoRow(label = "Plataforma:", value = it.platform)
                Spacer(Modifier.height(15.dp))

                InfoRow(label = "Desarrollador:", value = it.developer)
                Spacer(Modifier.height(15.dp))

                InfoRow(label = "Publisher:", value = it.publisher)
                Spacer(Modifier.height(15.dp))

                InfoRow(label = "Lanzamiento:", value = it.release_date)
                Spacer(Modifier.height(15.dp))

                // Descripción del juego
                Text(
                    text = "Descripción:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = it.short_description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Si el juego no está disponible, mostrar un loading spinner
            game ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Yellow)
            }
        }
    }
}


