package com.nityam.attendancesystem.data.model.response

data class AttendanceResponse (

    val attendanceDate: String,

    val clockIn: String?,

    val clockOut: String?,

    // backend sends Integer (nullable) - null until clock-out happens
    val workingMinutes: Int?,

    val status: String
)
