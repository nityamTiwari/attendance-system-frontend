package com.nityam.attendancesystem.presentation.history

import com.nityam.attendancesystem.data.model.response.AttendanceResponse

data class HistoryUiState(

    val records: List<AttendanceResponse> = emptyList(),

    val isLoading: Boolean = false,

    val isRefreshing: Boolean = false,

    val error: String? = null
) {
    val isEmpty: Boolean
        get() = !isLoading && error == null && records.isEmpty()
}
