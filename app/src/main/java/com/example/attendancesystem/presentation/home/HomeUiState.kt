package com.example.attendancesystem.presentation.home

import com.example.attendancesystem.data.model.response.AttendanceResponse

data class HomeUiState(

    val status: String = "NOT_STARTED",

    val clockIn: String = "--",

    val clockOut: String = "--",

    val workingTime: String = "00:00",

    val buttonText: String = "Clock In",

    val buttonEnabled: Boolean = true,

    val isLoading: Boolean = false,

    val isRefreshing: Boolean = false,

    val recentAttendance: List<AttendanceResponse> = emptyList(),

    val profileInitials: String = "?",

    val error: String? = null

)
