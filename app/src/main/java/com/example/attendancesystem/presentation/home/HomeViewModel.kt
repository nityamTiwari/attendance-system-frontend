package com.example.attendancesystem.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancesystem.data.model.response.TodayAttendanceResponse
import com.example.attendancesystem.data.repository.AttendanceRepository
import com.example.attendancesystem.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    private val _uistate = MutableStateFlow(HomeUiState())
    val uistate = _uistate.asStateFlow()

    init {
        loadDashboard(isRefresh = false)
    }

    /** Pull-to-refresh / retry entry point: reloads today's status and the recent history preview. */
    fun refresh() {
        loadDashboard(isRefresh = true)
    }

    fun getTodayAttendance() {
        loadDashboard(isRefresh = false)
    }

    private fun loadDashboard(isRefresh: Boolean) {
        viewModelScope.launch {

            _uistate.update {
                if (isRefresh) it.copy(isRefreshing = true, error = null)
                else it.copy(isLoading = true, error = null)
            }

            when (val result = repository.getTodayAttendance()) {

                is NetworkResult.Success -> {
                    applyToday(result.data)
                    loadRecentAttendance()
                }

                is NetworkResult.Error -> {
                    _uistate.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    private fun applyToday(response: TodayAttendanceResponse) {
        val buttonText = when (response.status) {
            TodayAttendanceResponse.STATUS_NOT_STARTED -> "Clock In"
            TodayAttendanceResponse.STATUS_ACTIVE -> "Clock Out"
            TodayAttendanceResponse.STATUS_COMPLETED -> "Completed"
            else -> "Clock In"
        }

        val buttonEnabled = response.status != TodayAttendanceResponse.STATUS_COMPLETED

        _uistate.update {
            it.copy(
                status = response.status,
                clockIn = response.clockIn ?: "--",
                clockOut = response.clockOut ?: "--",
                workingTime = response.workingMinutes?.let { minutes -> "$minutes min" } ?: "00:00",
                buttonText = buttonText,
                buttonEnabled = buttonEnabled,
                isLoading = false
            )
        }
    }

    private suspend fun loadRecentAttendance() {
        when (val result = repository.getHistory()) {
            is NetworkResult.Success -> {
                val recent = result.data
                    .sortedByDescending { it.attendanceDate }
                    .take(3)

                _uistate.update {
                    it.copy(recentAttendance = recent, isRefreshing = false)
                }
            }

            is NetworkResult.Error -> {
                // Today's status already loaded successfully - don't block the whole
                // screen on the history preview failing, just clear the loading spinners.
                _uistate.update { it.copy(isRefreshing = false) }
            }
        }
    }

    fun clockIn() {
        viewModelScope.launch {

            _uistate.update {
                it.copy(isLoading = true)
            }

            when (val result = repository.clockIn()) {
                is NetworkResult.Success -> {
                    loadDashboard(isRefresh = false)
                }

                is NetworkResult.Error -> {
                    _uistate.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }


    fun clockOut() {
        viewModelScope.launch {

            _uistate.update {
                it.copy(isLoading = true)
            }

            when (val result = repository.clockOut()) {

                is NetworkResult.Success -> {
                    loadDashboard(isRefresh = false)
                }

                is NetworkResult.Error -> {
                    _uistate.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }


    fun attendanceAction() {

        when (_uistate.value.status) {

            TodayAttendanceResponse.STATUS_NOT_STARTED -> clockIn()

            TodayAttendanceResponse.STATUS_ACTIVE -> clockOut()

            TodayAttendanceResponse.STATUS_COMPLETED -> Unit
        }
    }


}
