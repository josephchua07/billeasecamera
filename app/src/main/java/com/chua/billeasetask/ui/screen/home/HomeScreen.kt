package com.chua.billeasetask.ui.screen.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import coil.compose.rememberImagePainter
import com.chua.billeasetask.ui.screen.takephoto.TakePhotoViewModel

@Composable
fun StatefulHomeScreen(
    shouldShowPhoto: Boolean,
    homeViewModel: HomeViewModel,
    takePhotoViewModel: TakePhotoViewModel,
    navController: NavController,
) {
    with(homeViewModel) {
        StatelessHomeScreen(
            takePhotoViewModel.photoUris,
            shouldShowPhoto,
            onTakePhotoClicked = { interactions.takePhoto(navController) },
            onLogoutClicked = { interactions.logout(navController) },
        )
    }
}

@Composable
private fun StatelessHomeScreen(
    photoUris: List<Uri>,
    shouldShowPhoto: Boolean,
    onTakePhotoClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
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
                .padding(padding)
                .verticalScroll(rememberScrollState()),
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

            if (shouldShowPhoto)
                photoUris.forEach { uri ->
                    Image(
                        painter = rememberImagePainter(uri),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp),
                    )
                }

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