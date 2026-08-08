package com.example.attendancesystem

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.attendancesystem.navigation.AttendanceNavGraph
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AttendanceApplication : Application()
@Composable
fun AttendanceApp() {

    val navController = rememberNavController()

    AttendanceNavGraph(navController = navController)

//    val navController = rememberNavController()
//
//    AttendanceNavGraph(navController = navController)

//    var totalCount by remember { mutableStateOf(0) }
//
//    WelcomeCard(count = totalCount,
//        onIncrement = {totalCount++},
//        onDecrement = {totalCount--})


}


