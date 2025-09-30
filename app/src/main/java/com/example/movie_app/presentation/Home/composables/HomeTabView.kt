package com.example.movie_app.presentation.Home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.movie_app.domain.common.NetworkResponse
import com.example.movie_app.presentation.Home.HomeViewModel.HomeViewModel

@Composable
fun HomeScreenTabView(modifier: Modifier = Modifier, viewModel: HomeViewModel) {

    // Trigger the API call when the Composable is first launched
    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    val popularMoviesResult by viewModel.popularMoviesResult.observeAsState()

    when (val result = popularMoviesResult) {
        is NetworkResponse.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 columns like crossAxisCount in Flutter
                contentPadding = PaddingValues(8.dp)
            ) {
                items(result.data.results) { item ->
                    Card(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Column() {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://image.tmdb.org/t/p/w500${item.poster_path}")
                                    .build(),
                                contentDescription = "Description",
                                modifier = Modifier.size(200.dp)
                            )
                            Text(
                                text = "Item ${item.title}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }

        is NetworkResponse.Error -> Text(result.toString(), color = Color.Red)
        is NetworkResponse.Loading -> CircularProgressIndicator()
        null -> Text(text = "No Data")
    }

}