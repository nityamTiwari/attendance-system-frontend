package com.example.attendancesystem.data.model.response

/**
 * Maps com.nityam.attendancesystem.attendance.dto.ClockOutResponse { message, clockOut, workingMinutes }
 * NOTE: fields were previously `private val` with no defaults matching the backend's optional
 * nullability - made public (val) so callers outside this class can read them, and kept nullable
 * since the backend uses boxed types (String/Integer, both optional depending on flow).
 */
data class ClockOutResponse(
    val message: String? = null,
    val clockOut: String? = null,
    val workingMinutes: Int? = null
)
