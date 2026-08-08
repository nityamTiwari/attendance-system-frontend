package com.example.attendancesystem.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.attendancesystem.navigation.AppDestinations

@Composable
fun SplashRoute(

    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
){

    val navigationState by viewModel.navigationState.collectAsStateWithLifecycle()

    LaunchedEffect(navigationState) {

        when(navigationState){

            SplashNavigationState.Home -> {

                onNavigateToHome()
            }

            SplashNavigationState.Login -> {

                onNavigateToLogin()
            }

            SplashNavigationState.Loading -> Unit


        }

    }
    SplashScreen()
}