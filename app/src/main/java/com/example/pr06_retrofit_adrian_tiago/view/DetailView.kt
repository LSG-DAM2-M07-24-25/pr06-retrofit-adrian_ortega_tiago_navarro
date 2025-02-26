package com.example.pr06_retrofit_adrian_tiago.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntSize
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pr06_retrofit_adrian_tiago.R
import com.example.pr06_retrofit_adrian_tiago.viewmodel.MyViewModel
import com.example.pr06_retrofit_adrian_tiago.model.DatosAPIItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailView(
    navController: NavController,
    game: DatosAPIItem,
    modifier: Modifier = Modifier,
    MyViewModel: MyViewModel
) {
    //val allGames by MyViewModel.getGames().observeAsState(mutableListOf())
    val isFavourite by MyViewModel.isFavourite.observeAsState(false)
    var isLiking by remember { mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (game.title != null) {
                GlideImage(
                    model = game.thumbnail,
                    contentDescription = game.short_description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp)
                )
                Image(
                    painter = painterResource(id = if (isFavourite) R.drawable.corazonrojo else R.drawable.corazongris),
                    contentDescription = "Favourite",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.TopEnd)
                        .clickable(enabled = !isFavourite) {
                            isLiking = true
                            //TODO VARIABLES EN ROJO PORQUE LOS METOFOS NO ESTAN EN EL MODEL O EL VIEMODEL TODAVIA
                            val gameToUpdate = game.copy(isFavourite = !isFavourite)
                            if (!isFavourite) {
                                MyViewModel.likeGame(gameToUpdate) {
                                    isLiking = false
                                }
                            } else {
                                MyViewModel.freeGame(gameToUpdate) {
                                    isLiking = false
                                }
                            }
                        }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = game.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Genero del juego: ${game.genre}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Plataforma donde esta el juego: ${game.platform}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Text(
                    text = "Juego no encontrado",
                    fontSize = 22.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick =
                {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text("Tornar enrere")
            }
        }
    }
}