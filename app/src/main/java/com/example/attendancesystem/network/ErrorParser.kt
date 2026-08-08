package com.example.attendancesystem.network

import com.example.attendancesystem.data.model.response.ErrorResponse
import com.google.gson.Gson

object ErrorParser {

    fun parse(errorBody: String?): String {

        return try {

            val error = Gson().fromJson(
                errorBody,
                ErrorResponse::class.java
            )
            error.error ?: "Something went wrong"

        } catch (e: Exception) {

            "Something went wrong"
        }
    }
}
