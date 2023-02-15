package com.chua.billeasetask.ui.screen.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val uiState: ScreenState = ScreenState()

    val interactions = object : Interactions {
        override val onLogin: () -> Unit = {
            //TODO: mock login
            Log.d("username", uiState.username.value)
            Log.d("password", uiState.password.value)
        }
        override val onUsernameChanged: (String) -> Unit = {
            uiState.username.value = it
        }
        override val onPasswordChanged: (String) -> Unit = {
            uiState.password.value = it
        }
    }

    inner class ScreenState {
        var username: MutableState<String> = mutableStateOf("")
        var password: MutableState<String> = mutableStateOf("")
    }

    interface Interactions {
        val onLogin: () -> Unit
        val onUsernameChanged: (String) -> Unit
        val onPasswordChanged: (String) -> Unit
    }

}