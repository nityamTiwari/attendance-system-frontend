package com.example.attendancesystem.data.model.request

import org.jetbrains.annotations.NotNull

data class LoginRequest(
    val email: String = "",
    val password: String = ""
)



