package com.nityam.attendancesystem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nityam.attendancesystem.presentation.history.HistoryRoute
import com.nityam.attendancesystem.presentation.home.HomeRoute
import com.nityam.attendancesystem.presentation.login.LoginRoute
import com.nityam.attendancesystem.presentation.profile.ProfileRoute
import com.nityam.attendancesystem.presentation.register.RegisterRoute
import com.nityam.attendancesystem.presentation.splash.SplashRoute

@Composable
fun AttendanceNavGraph(navController: NavHostController,
                       startDestination : String = AppDestinations.Splash.route
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = AppDestinations.Splash.route){
           SplashRoute(
               onNavigateToLogin = {
                   navController.navigate(AppDestinations.Login.route){
                       popUpTo(AppDestinations.Login.route){
                           inclusive = true
                       }
                       launchSingleTop = true
                   }
               },
               onNavigateToHome = {

                   navController.navigate(AppDestinations.Home.route) {

                       popUpTo(AppDestinations.Splash.route) {
                           inclusive = true
                       }

                   }

               }
           )
       }

        composable(route = AppDestinations.Login.route){
            LoginRoute(
                onNavigateToHome = {
                    navController.navigate(AppDestinations.Splash.route){
                        popUpTo(AppDestinations.Login.route){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(AppDestinations.Register.route)
                }
            )
        }

        composable(route = AppDestinations.Home.route) {
            HomeRoute()
        }

        composable(route = AppDestinations.History.route) {
            HistoryRoute(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AppDestinations.Profile.route) {
            ProfileRoute(
                onBack = {
                    navController.popBackStack()
                },
                onLoggedOut = {
                    // Logout: clear JWT (already done in ProfileViewModel), return to Login,
                    // and clear the entire back stack so Back doesn't return to Home/Profile.
                    navController.navigate(AppDestinations.Login.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = AppDestinations.Register.route){
            RegisterRoute(
                onNavigateToLogin = {
                    navController.navigate(AppDestinations.Login.route){
                        launchSingleTop  = true
                    }
                }
            )
        }

    }

}
