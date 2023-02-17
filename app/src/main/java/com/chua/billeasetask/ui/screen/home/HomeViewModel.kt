package com.chua.billeasetask.ui.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.chua.billeasetask.ui.NavigationDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val uiState: ScreenState = ScreenState()

    val interactions = object : Interactions {
        override val takePhoto: (NavController) -> Unit = {
            it.navigate(NavigationDestination.TAKE_PHOTO.name)
        }
        override val logout: (NavController) -> Unit = {
            it.popBackStack()
        }

    }

    inner class ScreenState {
        var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
        var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
    }

    interface Interactions {
        val takePhoto: (NavController) -> Unit
        val logout: (NavController) -> Unit
    }
}