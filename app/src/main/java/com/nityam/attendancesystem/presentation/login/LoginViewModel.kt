package com.nityam.attendancesystem.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nityam.attendancesystem.data.datastore.TokenManager
import com.nityam.attendancesystem.data.model.request.LoginRequest
import com.nityam.attendancesystem.data.repository.AuthRepository
import com.nityam.attendancesystem.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

    private val repository: AuthRepository,
    private val tokenManager: TokenManager


) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                error = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                error = null
            )
        }
    }

    fun login() {


        if (_uiState.value.email.isBlank()) {
            _uiState.update {
                it.copy(error = "Email is required")
            }
            return
        }

        if (_uiState.value.password.isBlank()) {
            _uiState.update {
                it.copy(error = "Password is required")
            }
            return
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }

            val result = repository.login(

                LoginRequest(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )

            )

            when (result) {
                is NetworkResult.Success -> {

                    val loginResponse = result.data
                    tokenManager.saveToken(loginResponse.token)
                    tokenManager.saveEmailIfProfileMissing(_uiState.value.email)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoginSuccess = true,
                            error = null

                        )
                    }

                    Log.d("Login", "Success: ${loginResponse.token}")

                }

                is NetworkResult.Error -> {

                    _uiState.update {

                        it.copy(
                            isLoading = false,
                            error = result.message
                        )

                    }
                    Log.d("Login", result.message)

                }

            }

        }

    }

}