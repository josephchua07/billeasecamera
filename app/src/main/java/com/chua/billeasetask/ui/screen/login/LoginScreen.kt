package com.chua.billeasetask.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable

@Composable
fun StatefulLoginScreen(
    loginViewModel: LoginViewModel
) {
    with(loginViewModel) {
        StatelessLoginScreen(
            username = uiState.username.value,
            onUsernameChanged = interactions.onUsernameChanged,
            password = uiState.password.value,
            onPasswordChanged = interactions.onPasswordChanged,
            onButtonClicked = interactions.onLogin
        )
    }
}

@Composable
private fun StatelessLoginScreen(
    username: String,
    onUsernameChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onButtonClicked: () -> Unit
) {

    Column {
        TextField(
            value = username,
            onValueChange = onUsernameChanged,
            label = { Text("Login") })
        TextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = { Text("Password") })
        Button(onClick = onButtonClicked) {
            Text(text = "Continue")
        }
    }
}