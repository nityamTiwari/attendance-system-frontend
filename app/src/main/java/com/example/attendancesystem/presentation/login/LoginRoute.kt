package com.example.attendancesystem.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun LoginRoute(
    viewModel : LoginViewModel = hiltViewModel(),
    onNavigateToHome : () -> Unit,
    onNavigateToRegister: () -> Unit
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = {
            viewModel.login()
        },
        onNavigateToRegister = onNavigateToRegister
    )

    LaunchedEffect(uiState.isLoginSuccess) {
        if(uiState.isLoginSuccess){
            onNavigateToHome()
        }
    }
}