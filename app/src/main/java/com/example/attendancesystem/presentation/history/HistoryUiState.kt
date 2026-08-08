package com.example.attendancesystem.presentation.history

import com.example.attendancesystem.data.model.response.AttendanceResponse

data class HistoryUiState(

    val records: List<AttendanceResponse> = emptyList(),

    val isLoading: Boolean = false,

    val isRefreshing: Boolean = false,

    val error: String? = null
) {
    val isEmpty: Boolean
        get() = !isLoading && error == null && records.isEmpty()
}
