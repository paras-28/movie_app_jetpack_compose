package com.example.movie_app.presentation.Home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.movie_app.config.Routes
import com.example.movie_app.presentation.Home.HomeViewModel.HomeViewModel
import com.example.movie_app.presentation.Home.HomeViewModel.MovieUiState


@Composable
fun HomeScreenTabView(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavController
) {

//    // Trigger the API call when the Composable is first launched
//    LaunchedEffect(Unit) {
//        viewModel.getData()
//    }

    // Collect state using StateFlow
    val movieState by viewModel.popularMoviesState.collectAsState()
    /// approach with StateFlow
//    val popularMoviesResult by viewModel.popularMoviesResult.observeAsState()

    when (val result = movieState) {
        is MovieUiState.Idle -> {
            // Initial state - could show a welcome message
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to Movie App",
                    style = MaterialTheme.typography.h3
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.getData() }) {
                    Text("Load Movies")
                }
            }
        }

        is MovieUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 columns like crossAxisCount in Flutter
                contentPadding = PaddingValues(8.dp)
            ) {
                items(result.data.results) { movie ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(
                                onClick = {
                                    // Navigate to MovieDetailScreen with the movie object
                                    navController.navigate(
                                        Routes.MovieDetailScreen(movie = movie)
                                    )
                                }
                            )
                    ) {
                        Column() {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                                    .build(),
                                contentDescription = "Description",
                                modifier = Modifier.size(200.dp)
                            )
                            Text(
                                text = "Item ${movie.title}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }

        is MovieUiState.Error -> ErrorContentView(
            message = result.message,
            errorCode = result.errorCode,
            onRetry = { result.retry() }
        )

        is MovieUiState.Loading -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Loading movies...",
                style = MaterialTheme.typography.body2
            )
        }

        null -> Text(text = "No Data")
    }

}

private fun MovieUiState.Error.retry() {

}

