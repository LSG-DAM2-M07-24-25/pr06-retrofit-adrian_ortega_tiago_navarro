package com.example.pr06_retrofit_adrian_tiago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pr06_retrofit_adrian_tiago.ui.theme.PR06_Retrofit_Adrian_TiagoTheme
import com.example.pr06_retrofit_adrian_tiago.view.NavHost
import com.example.pr06_retrofit_adrian_tiago.viewmodel.MyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myViewModel by viewModels<MyViewModel>()
        enableEdgeToEdge()
        setContent {
            PR06_Retrofit_Adrian_TiagoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(Modifier.padding(innerPadding), navController, myViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PR06_Retrofit_Adrian_TiagoTheme {
        Greeting("Android")
    }
}