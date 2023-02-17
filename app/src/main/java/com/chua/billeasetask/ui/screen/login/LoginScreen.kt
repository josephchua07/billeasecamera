package com.chua.billeasetask.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StatefulLoginScreen(
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    with(loginViewModel) {
        StatelessLoginScreen(
            username = uiState.username.value,
            onUsernameChanged = interactions.onUsernameChanged,
            password = uiState.password.value,
            onPasswordChanged = interactions.onPasswordChanged,
            onButtonClicked = { interactions.onLogin(navController) }
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

    Scaffold(
        topBar = {
            TopAppBar(Modifier.height(150.dp)) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "TEST TASK", color = Color.White, fontSize = 20.sp)
                }
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.padding(top = 24.dp, bottom = 4.dp),
                value = username,
                onValueChange = onUsernameChanged,
                label = { Text("Login") })
            OutlinedTextField(
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp),
                value = password,
                onValueChange = onPasswordChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                onClick = onButtonClicked,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "Continue")
            }
        }
    }
}