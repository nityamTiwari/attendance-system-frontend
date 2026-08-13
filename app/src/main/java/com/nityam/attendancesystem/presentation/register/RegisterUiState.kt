package com.nityam.attendancesystem.presentation.register

import com.nityam.attendancesystem.common.Gender


data class RegisterUiState(
    val firstName : String = "",
    val lastName : String = "",
    val email : String = "",
    val password : String = "",
    val confirmPassword: String = "",
    val phone: String = "",
    val gender: Gender = Gender.MALE,
    val dob : String = "",

    val isLoading : Boolean = false,
    val error : String? = null,

    val isRegisterSuccess: Boolean = false

)
