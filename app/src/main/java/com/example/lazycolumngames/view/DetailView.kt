package com.example.lazycolumngames.view

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
    val configuration = LocalConfiguration.current

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = game?.title ?: "Detalles",
                style = MaterialTheme.typography.titleLarge

            )
            IconButton(onClick = { myViewModel.toggleFavourite() }) {
                Icon(
                    imageVector = if (game?.is_favourite == true) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = "Favourite",
                    tint = if (game?.is_favourite == true) Color.Red else Color.Gray
                )
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
                    .padding(64.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Género: ",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = it.genre,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Plataform: ",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = it.platform,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Desarrollador: ",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = it.developer,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Publisher: ",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = it.publisher,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Lanzamiento: ",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = it.release_date,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Descripcion: ",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = it.short_description,
                    style = MaterialTheme.typography.bodyLarge
                )

            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Yellow)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TabletDetailView(game: Juego?, myViewModel: MyViewModel) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
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

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = game?.title ?: "Detalles", fontSize = MaterialTheme.typography.headlineMedium.fontSize)
                IconButton(onClick = { myViewModel.toggleFavourite() }) {
                    Icon(
                        imageVector = if (game?.is_favourite == true) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        contentDescription = "Favourite",
                        tint = if (game?.is_favourite == true) Color.Red else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            game?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Genero: ",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = it.genre,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Plataform: ",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = it.platform,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Desarrollador: ",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = it.developer,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Publisher: ",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = it.publisher,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Lanzamiento: ",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = it.release_date,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Descripcion: ",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = it.short_description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

    }
}