package com.example.attendancesystem.network

import com.example.attendancesystem.data.model.response.AttendanceResponse
import com.example.attendancesystem.data.model.response.ClockInResponse
import com.example.attendancesystem.data.model.response.ClockOutResponse
import com.example.attendancesystem.data.model.response.TodayAttendanceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface AttendanceApi {

    @POST("api/v1/attendance/clockIn")
    suspend fun clockIn(): Response<ClockInResponse>

    @POST("api/v1/attendance/clockout")
    suspend fun clockOut(): Response<ClockOutResponse>

    @GET("api/v1/attendance/history")
    suspend fun attendanceHistory(): Response<List<AttendanceResponse>>

    @GET("api/v1/attendance/today")
    suspend fun getTodayAttendance(): Response<TodayAttendanceResponse>
}
