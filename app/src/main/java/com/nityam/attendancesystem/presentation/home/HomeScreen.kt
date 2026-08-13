package com.nityam.attendancesystem.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nityam.attendancesystem.data.model.response.AttendanceResponse
import com.nityam.attendancesystem.presentation.components.StatusChip
import com.nityam.attendancesystem.presentation.components.formatAttendanceDate
import com.nityam.attendancesystem.presentation.components.formatClockTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    onClockInClick: () -> Unit
) {

    // Bottom nav now lives in AttendanceApp as a persistent app-shell utility (see
    // presentation/components/AppBottomBar.kt) so it stays visible/functional across every
    // screen instead of being owned by Home alone - this Scaffold only needs a topBar.
    Scaffold(
        topBar = { DashboardTopBar(initials = uiState.profileInitials) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRetry) {
                            Text("Retry")
                        }
                    }
                }
            }
            else -> {
                // Main Content, wrapped for pull-to-refresh
                PullToRefreshBox(
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = onRefresh,
                    modifier = Modifier.fillMaxSize().padding(paddingValues)
                ) {
                    HomeDashboardContent(
                        uiState = uiState,
                        onClockInClick = onClockInClick
                    )
                }
            }
        }
    }
}

// ---  Header Component ---
@Composable
fun DashboardTopBar(initials: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Profile Avatar - same first/last-name-initials logic as the Profile screen, sourced
        // from the locally cached profile (TokenManager). Swap for a real employee photo/name
        // once a backend GET /me endpoint exists.
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        // Search Bar Placeholder
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Search your colleagues",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ---  Main Content Component ---
@Composable
fun HomeDashboardContent(uiState: HomeUiState, modifier: Modifier = Modifier, onClockInClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        GreetingSection()

        AttendanceCard(
            status = uiState.status,
            clockIn = uiState.clockIn,
            clockOut = uiState.clockOut,
            workingTime = uiState.workingTime,
            buttonText = uiState.buttonText,
            buttonEnabled = uiState.buttonEnabled,
            onClockInClick = onClockInClick
        )

        RecentAttendanceSection(recentAttendance = uiState.recentAttendance)
    }
}

// ---  Sub-Components ---
@Composable
fun GreetingSection() {
    Column {
        Text(
            text = "\uD83D\uDC4B Good Morning",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AttendanceCard(
    status: String,
    clockIn: String,
    clockOut: String,
    workingTime: String,
    buttonText: String,
    buttonEnabled: Boolean,
    onClockInClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header of the card
            Text(
                text = "Today's Attendance",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Divider()

            // Stats
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AttendanceInfoRow(label = "Status", value = status)
                AttendanceInfoRow(label = "Clock In", value = clockIn)
                AttendanceInfoRow(label = "Clock Out", value = clockOut)
                AttendanceInfoRow(label = "Working Time", value = workingTime)
            }

            // Action Button moved INSIDE the card
            Button(
                onClick = onClockInClick,
                enabled = buttonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {

                Text(buttonText)
            }
        }
    }
}

@Composable
fun AttendanceInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RecentAttendanceSection(recentAttendance: List<AttendanceResponse>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Recent Attendance",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (recentAttendance.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No attendance yet.", color = MaterialTheme.colorScheme.outline)
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                recentAttendance.forEach { record ->
                    RecentAttendanceRow(record)
                }
            }
        }
    }
}


@Composable
fun RecentAttendanceRow(record: AttendanceResponse) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = formatAttendanceDate(record.attendanceDate),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${formatClockTime(record.clockIn)} - ${formatClockTime(record.clockOut)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            StatusChip(status = record.status)
        }
    }
}