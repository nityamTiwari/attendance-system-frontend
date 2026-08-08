package com.example.attendancesystem

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.attendancesystem.navigation.AppDestinations
import com.example.attendancesystem.navigation.AttendanceNavGraph
import com.example.attendancesystem.presentation.components.AppBottomBar
import com.example.attendancesystem.presentation.components.isBottomBarRoute
import com.example.attendancesystem.session.SessionViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AttendanceApplication : Application()

@Composable
fun AttendanceApp() {

    val navController = rememberNavController()

    // App-scoped: reacts to a 401 from ANY screen by clearing the session and forcing the
    // user back to Login, regardless of which screen triggered it.
    val sessionViewModel: SessionViewModel = hiltViewModel()
    val forceLoggedOut by sessionViewModel.forceLoggedOut.collectAsStateWithLifecycle()

    LaunchedEffect(forceLoggedOut) {
        if (forceLoggedOut) {
            navController.navigate(AppDestinations.Login.route) {
                popUpTo(0) {
                    inclusive = true
                }
                launchSingleTop = true
            }
            sessionViewModel.onForcedLogoutHandled()
        }
    }

    // Bottom nav is a persistent app-shell utility, not something each screen owns: it lives
    // here, outside AttendanceNavGraph, so switching screens never re-mounts (or hides) it on
    // any of Home/History/Profile. Splash/Login/Register stay bar-free (see isBottomBarRoute).
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (isBottomBarRoute(currentRoute)) {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { scaffoldPadding ->

        Box(modifier = Modifier.padding(bottom = scaffoldPadding.calculateBottomPadding())) {
            AttendanceNavGraph(navController = navController)
        }
    }
}
