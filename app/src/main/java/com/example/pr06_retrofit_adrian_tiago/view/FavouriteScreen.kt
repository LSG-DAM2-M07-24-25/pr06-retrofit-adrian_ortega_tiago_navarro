package com.example.pr06_retrofit_adrian_tiago.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pr06_retrofit_adrian_tiago.model.DatosAPI
import com.example.pr06_retrofit_adrian_tiago.nav.Routes
import com.example.pr06_retrofit_adrian_tiago.viewmodel.MyViewModel
<<<<<<< HEAD
=======
import com.example.pr06_retrofit_adrian_tiago.viewmodel.ViewModel
>>>>>>> 0a120457c253f815f89fb8c229fd76b982d8f559

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(modifier: Modifier, navController: NavController, myViewModel: MyViewModel){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        val showLoading: Boolean by ViewModel.loading.observeAsState(true)
       // val favGame: MutableList<DatosAPI> by MyViewModel.favorites.observeAsState(mutableListOf()) ViewModel.getFavorites()

        if(showLoading) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = Color.Companion.Black
            )
        } else {
            LazyColumn() {
                items(favGame) { game ->
                        GameItem(game) {
                        navController.navigate(Routes.DetailView.createRoute(game.))
                    }
                }

                item(){
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Text("Tornar enrere")
                    }
                }
            }
        }

    }