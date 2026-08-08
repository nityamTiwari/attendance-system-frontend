package com.example.attendancesystem.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.attendancesystem.navigation.AppDestinations


private data class BottomNavEntry(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val enabled: Boolean = true
)

private val bottomNavEntries = listOf(
    BottomNavEntry(AppDestinations.Home.route, "Home", Icons.Filled.Home),
    BottomNavEntry("inbox", "Inbox", Icons.Outlined.Email, enabled = false),
    BottomNavEntry(AppDestinations.History.route, "Wall", Icons.Outlined.List),
    BottomNavEntry(AppDestinations.Profile.route, "Me", Icons.Outlined.Person),
    BottomNavEntry("my_team", "My Team", Icons.Outlined.Group, enabled = false)
)

/** Routes that should show the bottom bar. Auth/onboarding screens (Splash, Login, Register)
 * intentionally don't - there's no "app" to navigate around yet before you're signed in. */
fun isBottomBarRoute(route: String?): Boolean {
    return route == AppDestinations.Home.route ||
        route == AppDestinations.History.route ||
        route == AppDestinations.Profile.route
}

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        bottomNavEntries.forEach { entry ->
            NavigationBarItem(
                icon = { Icon(imageVector = entry.icon, contentDescription = entry.label) },
                label = { Text(entry.label) },
                selected = currentRoute == entry.route,
                onClick = {
                    if (entry.enabled && currentRoute != entry.route) {
                        onNavigate(entry.route)
                    }
                }
            )
        }
    }
}
