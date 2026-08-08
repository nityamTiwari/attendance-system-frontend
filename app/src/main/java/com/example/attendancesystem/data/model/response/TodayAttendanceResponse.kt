package com.example.attendancesystem.data.model.response

/**
 * Represents "today's" attendance state on the Home dashboard.
 *
 * NOTE (backend gap): the backend does NOT expose a GET /api/v1/attendance/today (or similar)
 * endpoint - only /clockIn, /clockout and GET /history exist
 * (see com.nityam.attendancesystem.attendance.controller.AttendanceController).
 * There is also no "NOT_STARTED" value in the backend's AttendanceStatus enum (only
 * ACTIVE/COMPLETED) - it's a client-only state used when no record exists for today.
 *
 * This is therefore derived client-side in AttendanceRepository.getTodayAttendance() by
 * fetching /history and picking out the entry whose attendanceDate matches today, rather than
 * calling a nonexistent endpoint. If a matching backend endpoint is added later, swap the
 * repository implementation back to a direct API call without needing to touch this model or
 * the ViewModel/UI that consume it.
 */
data class TodayAttendanceResponse(

    val attendanceDate: String,

    val clockIn: String?,

    val clockOut: String?,

    val workingMinutes: Int?,

    val status: String
) {
    companion object {
        const val STATUS_NOT_STARTED = "NOT_STARTED"
        const val STATUS_ACTIVE = "ACTIVE"
        const val STATUS_COMPLETED = "COMPLETED"

        fun notStarted(attendanceDate: String): TodayAttendanceResponse = TodayAttendanceResponse(
            attendanceDate = attendanceDate,
            clockIn = null,
            clockOut = null,
            workingMinutes = null,
            status = STATUS_NOT_STARTED
        )

        fun from(response: AttendanceResponse): TodayAttendanceResponse = TodayAttendanceResponse(
            attendanceDate = response.attendanceDate,
            clockIn = response.clockIn,
            clockOut = response.clockOut,
            workingMinutes = response.workingMinutes,
            status = response.status
        )
    }
}
