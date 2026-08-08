package com.example.attendancesystem.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun RegisterRoute(

    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit

) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    RegisterScreen(
        uiState = uiState,
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPhoneChange = viewModel::onPhoneChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onGenderChange = viewModel::onGenderChange,
        onRegisterClick = viewModel::register,
        onNavigateToLogin = onNavigateToLogin,
        onDobChange = viewModel::onDobChange
    )

    LaunchedEffect(uiState.isRegisterSuccess) {
        if(uiState.isRegisterSuccess){
            onNavigateToLogin()
        }
    }


}