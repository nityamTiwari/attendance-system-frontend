package com.example.attendancesystem.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancesystem.data.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tokenManager.profile.collect { profile ->
                _uiState.update { it.copy(profile = profile) }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.clearToken()
            tokenManager.clearProfile()
            _uiState.update { it.copy(isLoggedOut = true) }
        }
    }
}
