package com.example.movie_app.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movie_app.R


@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {
    // State variables for username and password
// Show toast message
    val context = LocalContext.current;
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        Image(
            painterResource(R.drawable.app_logo),
            contentDescription = "Login, Image",
            modifier = Modifier
                .size(100.dp)
                .clip(
                    shape = RoundedCornerShape(6.dp)
                ),
            contentScale = ContentScale.Crop,
        )
        Spacer(
            modifier = Modifier.height(screenHeight * 0.02f)
        )
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true
        )
        Button(
            onClick = {

                /*         navController.navigate(
                              route = Routes.ProfileDetail(
                                  userId = 24,
                                  userName = "Paras"
                              )
                          );

                          if (username.isEmpty() || password.isEmpty()) {
                              Toast.makeText(
                                  context, "Please enter both username and password", Toast.LENGTH_SHORT
                              ).show()
                          }*/
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

    }

}
