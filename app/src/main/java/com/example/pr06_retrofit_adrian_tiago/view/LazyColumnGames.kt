package com.example.pr06_retrofit_adrian_tiago.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pr06_retrofit_adrian_tiago.model.DatosAPI
import com.example.pr06_retrofit_adrian_tiago.viewmodel.MyViewModel


@Composable
fun LazyColumnGames(modifier: Modifier, navController: NavController, myViewModel: MyViewModel){
    val showLoading: Boolean by myViewModel.loading.observeAsState(true)
    val juegos: DatosAPI by myViewModel.games.observeAsState(DatosAPI(emptyList()))
    myViewModel.getGames()

    if (showLoading) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = Color.Cyan
            )
        }
    } else {
        LazyColumn {
            items(juegos.) {
                GameItem(game = it)
            }
        }
    }

}