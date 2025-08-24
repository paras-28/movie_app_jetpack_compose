package com.example.movie_app.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movie_app.presentation.HomeScreen
import com.example.movie_app.presentation.LoginScreen


@Composable
fun NavigationHost() {

    var navController = rememberNavController();

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen,
        modifier = Modifier
    ) {

        composable<Routes.LoginScreen> {
            LoginScreen(
                navController = navController
            )
        }

        composable<Routes.HomeScreen> {


            HomeScreen(
                navController = navController,
            )
        }
    }


}
