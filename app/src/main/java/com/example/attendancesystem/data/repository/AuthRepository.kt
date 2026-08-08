package com.example.attendancesystem.data.repository

import com.example.attendancesystem.data.model.request.LoginRequest
import com.example.attendancesystem.data.model.request.RegisterRequest
import com.example.attendancesystem.data.model.response.LoginResponse
import com.example.attendancesystem.data.model.response.RegisterResponse
import com.example.attendancesystem.network.AuthApi
import com.example.attendancesystem.network.ErrorParser
import com.example.attendancesystem.network.NetworkResult
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Response

@Singleton
class AuthRepository @Inject constructor(
    private val authApi : AuthApi
) {

    suspend fun login(
        request: LoginRequest
    ): NetworkResult<LoginResponse> {
        return try {
            val response = authApi.login(request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)


            } else {
                NetworkResult.Error(
                    ErrorParser.parse(
                        response.errorBody()?.string()
                    )
                )
            }


        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Something went wrong")
        }

    }



}