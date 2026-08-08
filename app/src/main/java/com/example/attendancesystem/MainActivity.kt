package com.example.attendancesystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.attendancesystem.ui.theme.AttendanceSystemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AttendanceSystemTheme {
                AttendanceApp()
            }
        }
    }
}

//@Composable
//fun AttendanceApp() {
//    Text("Attendance System")
//}