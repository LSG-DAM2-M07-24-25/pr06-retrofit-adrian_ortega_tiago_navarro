package com.example.lazycolumngames.view

import com.example.lazycolumngames.nav.Routes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.lazycolumngames.viewmodel.MyViewModel

@Composable
fun MyNavHost(modifier: Modifier, myNavController: NavHostController, myViewModel: MyViewModel) {
    NavHost(
        navController = myNavController,
        startDestination = Routes.LazyColumnGames.route
    ) {
        composable(Routes.LazyColumnGames.route) {
            LazyColumnGames(modifier, myNavController, myViewModel)
        }
        composable("DetailView/{gameJson}") { backStackEntry ->
            val gameJson = backStackEntry.arguments?.getString("gameJson")
            DetailView(gameJson, myViewModel) // Se pasa myViewModel
        }
    }
}