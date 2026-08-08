package com.example.attendancesystem.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancesystem.data.datastore.TokenManager
import com.example.attendancesystem.network.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _forceLoggedOut = MutableStateFlow(false)
    val forceLoggedOut = _forceLoggedOut.asStateFlow()

    init {
        viewModelScope.launch {
            sessionManager.sessionExpired.collect {
                tokenManager.clearToken()
                tokenManager.clearProfile()
                _forceLoggedOut.value = true
            }
        }
    }

    /** Called once navigation to Login has happened, so we don't re-trigger on recomposition. */
    fun onForcedLogoutHandled() {
        _forceLoggedOut.value = false
    }
}
