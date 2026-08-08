package com.example.attendancesystem.data.model.response

/**
 * Maps GET /api/v1/attendance/today, e.g.:
 * {
 *   "attendanceDate": "2026-08-08",
 *   "clockIn": null,
 *   "clockOut": null,
 *   "workingMinutes": 0,
 *   "status": "NOT_STARTED"
 * }
 */
data class TodayAttendanceResponse(

    val attendanceDate: String,

    val clockIn: String?,

    val clockOut: String?,

    val workingMinutes: Int,

    val status: String
) {
    companion object {
        const val STATUS_NOT_STARTED = "NOT_STARTED"
        const val STATUS_ACTIVE = "ACTIVE"
        const val STATUS_COMPLETED = "COMPLETED"
    }
}
