package com.example.attendancesystem.network

import com.example.attendancesystem.data.model.request.LoginRequest
import com.example.attendancesystem.data.model.request.RegisterRequest
import com.example.attendancesystem.data.model.response.LoginResponse
import com.example.attendancesystem.data.model.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthApi {

    @POST("api/v1/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

}