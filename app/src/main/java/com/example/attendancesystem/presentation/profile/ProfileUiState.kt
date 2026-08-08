package com.example.attendancesystem.presentation.profile

import com.example.attendancesystem.data.model.LocalProfile

data class ProfileUiState(
    val profile: LocalProfile = LocalProfile(),
    val isLoggedOut: Boolean = false
)
