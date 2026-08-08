package com.example.attendancesystem.data.repository

import com.example.attendancesystem.data.model.response.AttendanceResponse
import com.example.attendancesystem.data.model.response.ClockInResponse
import com.example.attendancesystem.data.model.response.ClockOutResponse
import com.example.attendancesystem.data.model.response.TodayAttendanceResponse
import com.example.attendancesystem.network.AttendanceApi
import com.example.attendancesystem.network.ErrorParser
import com.example.attendancesystem.network.NetworkResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AttendanceRepository @Inject constructor(
    private val attendanceApi: AttendanceApi
) {

    /**
     * The backend has no GET /today endpoint, so "today" is derived from the full
     * attendance history: find the entry whose attendanceDate matches today's date,
     * or fall back to a client-side NOT_STARTED state if there isn't one yet.
     */
    suspend fun getTodayAttendance(): NetworkResult<TodayAttendanceResponse> {

        return when (val result = getHistory()) {

            is NetworkResult.Success -> {
                val todayDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                val todayRecord = result.data.firstOrNull { it.attendanceDate == todayDate }

                val today = todayRecord?.let { TodayAttendanceResponse.from(it) }
                    ?: TodayAttendanceResponse.notStarted(todayDate)

                NetworkResult.Success(today)
            }

            is NetworkResult.Error -> NetworkResult.Error(result.message)
        }
    }

    suspend fun getHistory(): NetworkResult<List<AttendanceResponse>> {

        return try {

            val response = attendanceApi.attendanceHistory()

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

    suspend fun clockIn(): NetworkResult<ClockInResponse> {

        return try {

            val response = attendanceApi.clockIn()

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


    suspend fun clockOut(): NetworkResult<ClockOutResponse> {

        return try {
            val response = attendanceApi.clockOut()

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
