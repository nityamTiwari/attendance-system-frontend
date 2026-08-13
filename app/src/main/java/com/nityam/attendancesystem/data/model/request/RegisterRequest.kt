package com.nityam.attendancesystem.data.model.request

import com.nityam.attendancesystem.common.Gender

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val dob : String,
    val gender: Gender
)


