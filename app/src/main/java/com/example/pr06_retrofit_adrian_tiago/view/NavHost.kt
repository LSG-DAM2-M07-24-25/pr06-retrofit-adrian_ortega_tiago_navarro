package com.example.pr06_retrofit_adrian_tiago.view

//import DetailView
import com.example.pr06_retrofit_adrian_tiago.nav.Routes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun NavHost(modifier: Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.LazyColumnGames.route
    ) {
        composable(Routes.LazyColumnGames.route) {
            LazyColumnGames(modifier, navController)
        }

        composable(
            Routes.DetailView.route,
            arguments = listOf(
                navArgument("gameName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            DetailView(
                navController,
                backStackEntry.arguments?.getString("gameName").orEmpty(),
                modifier
            )
        }
    }
}