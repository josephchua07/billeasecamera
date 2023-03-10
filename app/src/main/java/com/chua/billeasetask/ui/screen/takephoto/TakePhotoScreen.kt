package com.chua.billeasetask.ui.screen.takephoto

import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import com.chua.billeasetask.util.getCameraProvider
import java.io.File
import java.util.concurrent.Executor

@Composable
fun StatefulTakePhotoScreen(
    takePhotoViewModel: TakePhotoViewModel,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: () -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    takePhotoViewModel.setCamera(
        imageCapture,
        outputDirectory,
        executor
    )

    StatelessTakePhotoScreen(previewView = previewView, takePhotoViewModel.takingPicture.value) {
        takePhotoViewModel.interactions.onTakePhoto(
            onImageCaptured,
            onError
        )
    }
}


@Composable
private fun StatelessTakePhotoScreen(
    previewView: PreviewView,
    takingPicture: Boolean,
    onTakePhoto: () -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        if (!takingPicture) {
            IconButton(
                modifier = Modifier.padding(bottom = 100.dp),
                onClick = {
                    Log.i("camera", "ON CLICK")
                    onTakePhoto()
                },
                content = {
                    Icon(
                        imageVector = Icons.Sharp.Lens,
                        contentDescription = "Take picture",
                        tint = Color.White,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(1.dp)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
            )
        } else {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "We are now taking your photos...",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun StatelessDoneTakingPhotoButton(photoUris: List<Uri>, onDone: () -> Unit) {

    Scaffold { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                onClick = onDone
            ) {
                Text(text = "Done")
            }
        }
    }


}
