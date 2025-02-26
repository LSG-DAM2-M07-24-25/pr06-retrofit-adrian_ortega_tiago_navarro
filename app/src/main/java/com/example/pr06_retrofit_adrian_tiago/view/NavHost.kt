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
import com.example.pr06_retrofit_adrian_tiago.viewmodel.MyViewModel

@Composable
fun NavHost(modifier: Modifier, navController: NavHostController, myViewModel: MyViewModel) {
    NavHost(
        navController = navController,
        startDestination = Routes.LazyColumnGames.route
    ) {
        composable(Routes.LazyColumnGames.route) {
            LazyColumnGames(navController, myViewModel)
        }

        composable(
            Routes.DetailView.route,
            arguments = listOf(
                navArgument("gameName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            DetailView(
                navController,
<<<<<<< HEAD
                backStackEntry.arguments?.getString("gameName").orEmpty()
=======
                backStackEntry.arguments?.getString("gameName").orEmpty(),
                modifier = modifier,
                MyViewModel
>>>>>>> 0a120457c253f815f89fb8c229fd76b982d8f559
            )
        }
    }
}