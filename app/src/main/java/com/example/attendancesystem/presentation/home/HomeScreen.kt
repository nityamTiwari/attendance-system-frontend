package com.example.attendancesystem.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
// Requires androidx.compose.material3:material3 1.3.0+ (stable PullToRefreshBox API).
// If the module's Material3 version predates this, either bump it or drop this import
// and the PullToRefreshBox wrapper below in favor of a manual refresh button.
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.attendancesystem.data.model.response.AttendanceResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    onClockInClick: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToProfile: () -> Unit
) {

    // Scaffold manages the layout of TopBar, BottomBar, and Content
    Scaffold(
        topBar = { DashboardTopBar() },
        bottomBar = {
            DashboardBottomBar(
                onHistoryClick = onNavigateToHistory,
                onProfileClick = onNavigateToProfile
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        // Handle Loading and Error states gracefully
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
fun DashboardTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Profile Avatar Placeholder
        // TODO: backend has no GET /me or /profile endpoint yet, so the initials/name here
        // can't be populated from real data. Swap this for the logged-in employee's initials
        // once a profile endpoint exists.
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "NK",
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

// ---  Bottom Navigation Component ---
@Composable
fun DashboardBottomBar(onHistoryClick: () -> Unit, onProfileClick: () -> Unit) {
    NavigationBar {
        BottomNavItem(label = "Home", icon = Icons.Filled.Home, selected = true)
        BottomNavItem(label = "Inbox", icon = Icons.Outlined.Email, selected = false)
        // "Wall" doubles as the entry point into Attendance History for now - the list icon
        // is the closest fit among the existing placeholder items. TODO: revisit if/when
        // Inbox/My Team get real screens of their own.
        BottomNavItem(label = "Wall", icon = Icons.Outlined.List, selected = false, onClick = onHistoryClick)
        BottomNavItem(label = "Me", icon = Icons.Outlined.Person, selected = false, onClick = onProfileClick)
        BottomNavItem(label = "My Team", icon = Icons.Outlined.Group, selected = false)
    }
}

@Composable
fun RowScope.BottomNavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit = { /* TODO: Implement Navigation */ }
) {
    NavigationBarItem(
        icon = { Icon(imageVector = icon, contentDescription = label) },
        label = { Text(label) },
        selected = selected,
        onClick = onClick
    )
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
            Column {
                Text(text = record.attendanceDate, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    text = "${record.clockIn ?: "--"} - ${record.clockOut ?: "--"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            Text(text = record.status, style = MaterialTheme.typography.labelMedium)
        }
    }
}
