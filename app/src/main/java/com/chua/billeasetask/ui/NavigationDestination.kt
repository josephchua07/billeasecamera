package com.chua.billeasetask.ui

sealed class NavigationDestination(val name: String) {

    object LOGIN : NavigationDestination("login")
    object HOME : NavigationDestination("home")
    object TAKE_PHOTO : NavigationDestination("take_photo")

}
