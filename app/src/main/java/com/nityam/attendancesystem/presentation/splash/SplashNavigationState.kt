package com.nityam.attendancesystem.presentation.splash


sealed class  SplashNavigationState{
    data object Loading : SplashNavigationState()
    data object  Login : SplashNavigationState()
    data object Home : SplashNavigationState()
}