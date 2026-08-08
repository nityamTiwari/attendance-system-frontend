package com.example.attendancesystem.navigation

sealed class AppDestinations(val route : String){
    data object Splash: AppDestinations("splash")
    data object Home: AppDestinations("home")
    data object Login: AppDestinations("login")

    data object Register: AppDestinations("register")

    data object History: AppDestinations("history")

    data object Profile: AppDestinations("profile")
}