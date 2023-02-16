package com.chua.billeasetask.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun StatefulHomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    content: @Composable (ColumnScope.() -> Unit)
) {
    with(homeViewModel) {
        StatelessHomeScreen(
            onTakePhotoClicked = { interactions.takePhoto(navController) },
            onLogoutClicked = { interactions.logout(navController) },
            content = content
        )
    }
}

@Composable
private fun StatelessHomeScreen(
    onTakePhotoClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Column {
        Button(onClick = onTakePhotoClicked) {
            Text(text = "Take Photo")
        }

        content()

        Button(onClick = onLogoutClicked) {
            Text(text = "Logout")
        }
    }
}