package com.example.movie_app.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.movie_app.presentation.Home.HomeScreen
import com.example.movie_app.presentation.Home.HomeViewModel.HomeViewModel
import com.example.movie_app.presentation.LoginScreen
import com.example.movie_app.presentation.movie_detail.MovieDetailScreen
import com.example.movie_app.presentation.sharedViewModel.MovieSharedMovieViewModel


@Composable
fun NavigationHost(viewModel: HomeViewModel) {

    var navController: NavHostController = rememberNavController();

    // Create the shared ViewModel at navigation level
    val movieSharedViewModel: MovieSharedMovieViewModel = hiltViewModel()

    NavHost(
        navController = navController, startDestination = Routes.HomeScreen, modifier = Modifier
    ) {

        composable<Routes.LoginScreen> {
            LoginScreen(
                navController = navController
            )
        }

        composable<Routes.HomeScreen> {
            HomeScreen(
                navController = navController, viewModel = viewModel,
                movieSharedViewModel = movieSharedViewModel
            )
        }

        composable<Routes.MovieDetailScreen> { backStackEntry ->
            val movieDetailRoute = backStackEntry.toRoute<Routes.MovieDetailScreen>()
            MovieDetailScreen(
                navController = navController,
                movieSharedViewModel = movieSharedViewModel
            )
        }


    }
}
