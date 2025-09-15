package com.example.movie_app.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier, value: String, onValueChange: (String) -> Unit,
    labelText: String,
    error: String,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
//        onValueChange = { username = it },
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(20),
        isError = error.isNotEmpty(),
        supportingText = {
            if (error.isNotEmpty()) {
                Text(
                    text = error,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error
                )
            }
        },
    )
}