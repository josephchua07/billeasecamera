package com.chua.billeasetask

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chua.billeasetask.ui.NavigationDestination
import com.chua.billeasetask.ui.screen.home.HomeViewModel
import com.chua.billeasetask.ui.screen.home.StatefulHomeScreen
import com.chua.billeasetask.ui.screen.login.LoginViewModel
import com.chua.billeasetask.ui.screen.login.StatefulLoginScreen
import com.chua.billeasetask.ui.screen.takephoto.CameraView
import com.chua.billeasetask.ui.theme.BilleaseTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("camera", "Permission granted")
            shouldShowCamera.value = true
        } else {
            Log.i("camera", "Permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BilleaseTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "login") {

                        composable(NavigationDestination.LOGIN.name) {
                            StatefulLoginScreen(
                                loginViewModel = loginViewModel,
                                navController = navController
                            )
                        }

                        composable(NavigationDestination.HOME.name) {
                            StatefulHomeScreen(
                                homeViewModel = homeViewModel,
                                navController = navController
                            )
                        }

                        composable(NavigationDestination.TAKE_PHOTO.name) {
                            if (shouldShowCamera.value) {
                                CameraView(
                                    outputDirectory = outputDirectory,
                                    executor = cameraExecutor,
                                    onImageCaptured = ::handleImageCapture,
                                    onError = { Log.e("camera", "View error:", it) }
                                )
                            }
                        }

                    }
                }
            }
        }

        requestCameraPermission()

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("camera", "Permission previously granted")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                CAMERA
            ) -> Log.i("camera", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(CAMERA)
        }
    }

    private fun handleImageCapture(uri: Uri) {
        Log.i("camera", "Image captured: $uri")
        shouldShowCamera.value = false
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}