package com.nityam.attendancesystem.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()

) {

    val uiState by viewModel.uistate.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onRetry = viewModel::getTodayAttendance,
        onRefresh = viewModel::refresh,
        onClockInClick = viewModel::attendanceAction
    )

}
