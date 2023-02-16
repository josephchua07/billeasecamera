package com.chua.billeasetask.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.chua.billeasetask.ui.NavigationDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    //TODO CREATE UI STATE

    val interactions = object : Interactions {
        override val takePhoto: (NavController) -> Unit = {
            it.navigate(NavigationDestination.TAKE_PHOTO.name)
        }
        override val logout: (NavController) -> Unit = {
            it.popBackStack()
        }

    }

    interface Interactions {
        val takePhoto: (NavController) -> Unit
        val logout: (NavController) -> Unit
    }
}