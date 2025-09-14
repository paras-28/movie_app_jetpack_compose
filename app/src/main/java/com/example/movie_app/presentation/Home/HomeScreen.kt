package com.example.movie_app.presentation.Home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movie_app.domain.common.NetworkResponse
import com.example.movie_app.presentation.Home.composables.BottomNavigationBar
import com.example.movie_app.presentation.Home.composables.NavigationItems
import com.example.movie_app.presentation.Home.controller.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel
) {

    ///List of Navigation Items that will be clicked
    val items = listOf(
        NavigationItems(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItems(
            title = "Info",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        ),
        NavigationItems(
            title = "Edit",
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit,
            badgeCount = 105
        ),
        NavigationItems(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
    )

    //Remember Clicked item state
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

//Remember the State of the drawer. Closed/ Opened
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()


    // For Navigation Controller
    val navController = rememberNavController();


    /// api call


// Trigger the API call when the Composable is first launched
    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp)) //space (margin) from top
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            //  navController.navigate(item.route)

                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {  // Show Badge
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding) //padding between items
                    )
                }

            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(

            floatingActionButton =
                {
                    FloatingActionButton(onClick = {

                        viewModel.getData()

                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Add")
                    }
                },
            bottomBar = {
                BottomNavigationBar(navController = navController)
            },
            modifier = modifier,
            topBar = { //TopBar to show title
                TopAppBar(
                    title = {
                        Text(text = "Movie App")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(  //Show Menu Icon on TopBar
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPaddding ->
            Column(
                modifier = Modifier
                    .padding(innerPaddding)
                    .padding(16.dp)
            )
            {
//                AppNavigationHost(navController = navController)

                val popularMoviesResult by viewModel.popularMoviesResult.observeAsState()

                when (val result = popularMoviesResult) {
                    is NetworkResponse.Success -> Column() {
                        Text(text = "Popular Movies:")
                        Spacer(modifier = Modifier.height(8.dp))
                        result.data.results.forEach { movie ->
                            Text(text = movie.title ?: "No Title")
                        }
                    }

                    is NetworkResponse.Error -> Text(result.toString(), color = Color.Red)
                    NetworkResponse.Loading -> CircularProgressIndicator()
                    null -> Text(text = "Currently Null")
                }
            }


        }

    }


}



