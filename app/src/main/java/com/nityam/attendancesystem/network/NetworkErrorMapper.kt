package com.nityam.attendancesystem.network

import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


object NetworkErrorMapper {

    fun <T> fromResponse(response: Response<T>): NetworkResult.Error {

        val backendMessage = ErrorParser.parse(response.errorBody()?.string())

        return when (response.code()) {

            401 -> NetworkResult.Error("Your session has expired. Please log in again.")

            403 -> NetworkResult.Error("You don't have permission to perform this action.")

            404 -> NetworkResult.Error("Requested resource was not found.")

            in 500..599 -> NetworkResult.Error("Something went wrong on our end. Please try again later.")

            // 400/409/etc - these carry a real, useful message from GlobalExceptionHandler
            // (e.g. "Email already exists", "Already clocked in today").
            else -> NetworkResult.Error(backendMessage)
        }
    }

    fun fromException(e: Exception): NetworkResult.Error {

        return when (e) {

            is SocketTimeoutException ->
                NetworkResult.Error("Request timed out. Please check your connection and try again.")

            is UnknownHostException ->
                NetworkResult.Error("No internet connection. Please check your network and try again.")

            is IOException ->
                NetworkResult.Error("No internet connection. Please check your network and try again.")

            else -> NetworkResult.Error("Something went wrong. Please try again.")
        }
    }
}
