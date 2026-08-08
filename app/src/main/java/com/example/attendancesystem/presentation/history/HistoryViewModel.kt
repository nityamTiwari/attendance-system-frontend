package com.example.attendancesystem.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancesystem.data.repository.AttendanceRepository
import com.example.attendancesystem.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadHistory(isRefresh = false)
    }

    fun retry() {
        loadHistory(isRefresh = false)
    }

    fun refresh() {
        loadHistory(isRefresh = true)
    }

    private fun loadHistory(isRefresh: Boolean) {
        viewModelScope.launch {

            _uiState.update {
                if (isRefresh) it.copy(isRefreshing = true, error = null)
                else it.copy(isLoading = true, error = null)
            }

            when (val result = repository.getHistory()) {

                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            records = result.data.sortedByDescending { record -> record.attendanceDate },
                            isLoading = false,
                            isRefreshing = false
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
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
}
