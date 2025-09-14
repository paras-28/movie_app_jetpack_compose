package com.example.movie_app.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movie_app.presentation.Home.HomeScreen
import com.example.movie_app.presentation.Home.controller.HomeViewModel
import com.example.movie_app.presentation.LoginScreen


@Composable
fun NavigationHost(viewModel: HomeViewModel) {

    var navController = rememberNavController();

    NavHost(
        navController = navController,
        startDestination = Routes.LoginScreen,
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
                viewModel = viewModel
            )
        }
    }


}
