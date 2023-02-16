package com.chua.billeasetask.ui.screen.takephoto

import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class TakePhotoViewModel @Inject constructor() : ViewModel() {

    val interactions = object : Interactions {
        override val onTakePhoto: (
            String,
            ImageCapture,
            File,
            Executor,
            (Uri) -> Unit,
            (ImageCaptureException) -> Unit
        ) -> Unit =
            { filenameFormat, imageCapture, outputDirectory, executor, onImageCaptured, onError ->

                viewModelScope.launch {

                    val photoFile = File(
                        outputDirectory,
                        SimpleDateFormat(
                            filenameFormat,
                            Locale.US
                        ).format(System.currentTimeMillis()) + ".jpg"
                    )

                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                    imageCapture.takePicture(
                        outputOptions,
                        executor,
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onError(exception: ImageCaptureException) {
                                Log.e("camera", "Take photo error:", exception)
                                onError(exception)
                            }

                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                val savedUri = Uri.fromFile(photoFile)
                                onImageCaptured(savedUri)
                            }
                        })
                }
            }

        override val onDoneTakingPhoto: (NavController) -> Unit = {
            it.popBackStack()
        }

    }

    interface Interactions {
        val onTakePhoto: (
            String,
            ImageCapture,
            File,
            Executor,
            (Uri) -> Unit,
            (ImageCaptureException) -> Unit
        ) -> Unit

        val onDoneTakingPhoto: (NavController) -> Unit
    }

}