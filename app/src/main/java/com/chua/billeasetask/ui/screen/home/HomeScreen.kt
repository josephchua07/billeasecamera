package com.chua.billeasetask.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun StatefulHomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    with(homeViewModel) {
        StatelessHomeScreen(
            onTakePhotoClicked = { interactions.takePhoto(navController) },
            onLogoutClicked = { interactions.logout(navController) },
        )
    }
}

@Composable
private fun StatelessHomeScreen(
    onTakePhotoClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
) {
    Column {
        Button(onClick = onTakePhotoClicked) {
            Text(text = "Take Photo")
        }

        Button(onClick = onLogoutClicked) {
            Text(text = "Logout")
        }
    }
}