package com.example.movie_app.presentation.Home.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movie_app.presentation.Home.HomeViewModel.HomeViewModel

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
}

@Composable
fun BottomNavigationBarNavigationHost(
    bottomNavigationController: NavHostController,
    navigationController: NavHostController,
    viewModel: HomeViewModel
) {
    NavHost(bottomNavigationController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreenTabView(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
                viewModel = viewModel,
                navController = navigationController
            )
        }
        composable(BottomNavItem.Search.route) {
            Text(
                "Search",
                modifier = Modifier
                    .wrapContentSize(),
            )
        }
    }
}


@Composable
fun BottomNavigationBar(bottomNavigationController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by bottomNavigationController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        listOf(
            BottomNavItem.Home, BottomNavItem.Search,
        ).forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    bottomNavigationController.navigate(item.route) {
                        popUpTo(bottomNavigationController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) })
        }
    }
}