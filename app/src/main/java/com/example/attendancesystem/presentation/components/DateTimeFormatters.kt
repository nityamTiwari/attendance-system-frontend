package com.example.attendancesystem.presentation.components

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
private val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")


fun formatClockTime(raw: String?): String {
    if (raw.isNullOrBlank()) return "--"

    return try {
        LocalDateTime.parse(raw).format(TIME_FORMAT)
    } catch (e: Exception) {
        raw
    }
}

fun formatAttendanceDate(raw: String): String {
    return try {
        LocalDate.parse(raw).format(DATE_FORMAT)
    } catch (e: Exception) {
        raw
    }
}

fun formatWorkingMinutes(totalMinutes: Int): String {
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return "${hours}h ${minutes}m"
}