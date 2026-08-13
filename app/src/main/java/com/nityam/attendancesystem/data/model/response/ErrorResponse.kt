package com.nityam.attendancesystem.data.model.response

/**
 * Maps the backend's GlobalExceptionHandler error body: {"error": "<message>"}
 * NOTE: backend key is "error", not "message" - keep in sync with
 * com.nityam.attendancesystem.exception.GlobalExceptionHandler
 */
data class ErrorResponse(
    val error: String? = null
)
