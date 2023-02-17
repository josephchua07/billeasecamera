package com.chua.billeasetask.ui.screen.takephoto

import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class TakePhotoViewModel @Inject constructor() : ViewModel() {

    var photoUris: MutableList<Uri> = mutableListOf()

    var photoCapturedNumber = 0
    lateinit var imageCapture: ImageCapture
    lateinit var outputDirectory: File
    lateinit var executor: Executor

    fun setCamera(imageCapture: ImageCapture, outputDirectory: File, executor: Executor) {
        this.imageCapture = imageCapture
        this.outputDirectory = outputDirectory
        this.executor = executor
    }

    fun capturePhoto(
        onImageCaptured: () -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        val photoFile = File(
            outputDirectory,
            "PhotoCaptured$photoCapturedNumber.jpg"
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
                    photoCapturedNumber++
                    val savedUri = Uri.fromFile(photoFile)
                    photoUris.add(savedUri)
                    if (photoUris.size >= 3)
                        onImageCaptured()
                }
            })
    }

    val interactions = object : Interactions {
        override val onTakePhoto: (
                () -> Unit,
                (ImageCaptureException) -> Unit
        ) -> Unit =
            { onImageCaptured, onError ->
                viewModelScope.launch {
                    photoUris.clear()
                    repeat(3) {
                        capturePhoto(onImageCaptured, onError)
                        delay(2000L)
                    }
                }
            }

        override val onDoneTakingPhoto: (NavController) -> Unit = {
            it.popBackStack()
        }

    }

    interface Interactions {
        val onTakePhoto: (
                () -> Unit,
                (ImageCaptureException) -> Unit
        ) -> Unit

        val onDoneTakingPhoto: (NavController) -> Unit
    }

}