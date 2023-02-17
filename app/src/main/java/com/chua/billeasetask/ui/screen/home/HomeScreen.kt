package com.chua.billeasetask.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Scaffold(
        topBar = {
            TopAppBar {
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
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(20.dp),
                onClick = onTakePhotoClicked
            ) {
                Text(text = "Take Photo")
            }

            content()

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(20.dp),
                onClick = onLogoutClicked
            ) {
                Text(text = "Logout")
            }
        }
    }
}