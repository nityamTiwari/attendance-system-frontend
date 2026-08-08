package com.example.attendancesystem.data.repository

import com.example.attendancesystem.data.model.request.LoginRequest
import com.example.attendancesystem.data.model.request.RegisterRequest
import com.example.attendancesystem.data.model.response.LoginResponse
import com.example.attendancesystem.data.model.response.RegisterResponse
import com.example.attendancesystem.network.AuthApi
import com.example.attendancesystem.network.NetworkErrorMapper
import com.example.attendancesystem.network.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

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
                NetworkErrorMapper.fromResponse(response)
            }

        } catch (e: Exception) {
            NetworkErrorMapper.fromException(e)
        }

    }


    suspend fun register(
        request: RegisterRequest
    ): NetworkResult<RegisterResponse> {

        return try{

            val response = authApi.register(request)

            if(response.isSuccessful && response.body()!=null){
                NetworkResult.Success(response.body()!!)
            }else{
                NetworkErrorMapper.fromResponse(response)
            }
        }catch (e: Exception) {

            NetworkErrorMapper.fromException(e)
        }
    }

}
