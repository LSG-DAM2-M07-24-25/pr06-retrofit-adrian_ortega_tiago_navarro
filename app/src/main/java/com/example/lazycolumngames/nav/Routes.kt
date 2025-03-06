package com.example.lazycolumngames.nav

import android.net.Uri

sealed class Routes(val route: String) {
    object LazyColumnGames: Routes("LazyColumnGames")

   object DetailView: Routes("DetailView/{gameJson") {
       //fun createRoute(gameJson: String): String = "DetailView/${Uri.encode(gameJson)}"
       fun createRoute(gameJson: String): String = "DetailView/$gameJson"
   }
}