package com.example.attendancesystem.network

import com.example.attendancesystem.data.model.response.AttendanceResponse
import com.example.attendancesystem.data.model.response.ClockInResponse
import com.example.attendancesystem.data.model.response.ClockOutResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Mirrors com.nityam.attendancesystem.attendance.controller.AttendanceController exactly.
 * NOTE: there is no GET /today on the backend - "today" is derived in AttendanceRepository
 * from attendanceHistory(). Do not add a call here unless the backend adds the route.
 */
interface AttendanceApi {

    @POST("api/v1/attendance/clockIn")
    suspend fun clockIn(): Response<ClockInResponse>

    @POST("api/v1/attendance/clockout")
    suspend fun clockOut(): Response<ClockOutResponse>

    // backend route is GET, was incorrectly @POST before - would have returned 405
    @GET("api/v1/attendance/history")
    suspend fun attendanceHistory(): Response<List<AttendanceResponse>>
}
