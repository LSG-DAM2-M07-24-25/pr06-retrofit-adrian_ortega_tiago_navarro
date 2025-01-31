package com.example.pr06_retrofit_adrian_tiago.nav

sealed class Routes(val route: String) {
    object LazyColumnGames: Routes("LazyColumnGames")

    object DetailView: Routes("DetailView/{gameName") {
        fun createRoute(gameName: String) = "DetailView/$gameName"
    }
}