package com.chua.billeasetask.ui.screen.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.chua.billeasetask.data.remote.AuthorizationApi
import com.chua.billeasetask.ui.NavigationDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val api: AuthorizationApi) : ViewModel() {

    val uiState: ScreenState = ScreenState()

    val interactions = object : Interactions {
        override val onLogin: (NavController) -> Unit = {
            Log.d("username", uiState.username.value)
            Log.d("password", uiState.password.value)
            if (uiState.username.value.isNotBlank() && uiState.password.value.isNotBlank()) {
                login(uiState.username.value, uiState.password.value) {
                    it.navigate(NavigationDestination.HOME.name)
                }
            }
        }
        override val onUsernameChanged: (String) -> Unit = {
            uiState.username.value = it
        }
        override val onPasswordChanged: (String) -> Unit = {
            uiState.password.value = it
        }
    }

    private fun login(username: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = api.login()
                if (response.isSuccessful) {
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("login", e.message.toString())
            }
        }
    }

    inner class ScreenState {
        var username: MutableState<String> = mutableStateOf("")
        var password: MutableState<String> = mutableStateOf("")
    }

    interface Interactions {
        val onLogin: (NavController) -> Unit
        val onUsernameChanged: (String) -> Unit
        val onPasswordChanged: (String) -> Unit
    }

}