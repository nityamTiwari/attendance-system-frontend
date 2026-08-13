package com.nityam.attendancesystem.presentation.profile

import com.nityam.attendancesystem.data.model.LocalProfile

data class ProfileUiState(
    val profile: LocalProfile = LocalProfile(),
    val isLoggedOut: Boolean = false
)
