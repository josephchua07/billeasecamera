package com.chua.billeasetask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chua.billeasetask.ui.NavigationDestination
import com.chua.billeasetask.ui.screen.home.HomeViewModel
import com.chua.billeasetask.ui.screen.home.StatefulHomeScreen
import com.chua.billeasetask.ui.screen.login.LoginViewModel
import com.chua.billeasetask.ui.screen.login.StatefulLoginScreen
import com.chua.billeasetask.ui.screen.takephoto.TakePhotoScreen
import com.chua.billeasetask.ui.theme.BilleaseTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

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
                            TakePhotoScreen()
                        }

                    }
                }
            }
        }
    }
}