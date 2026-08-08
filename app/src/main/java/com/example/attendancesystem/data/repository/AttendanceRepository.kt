package com.example.attendancesystem.data.repository

import com.example.attendancesystem.data.model.response.AttendanceResponse
import com.example.attendancesystem.data.model.response.ClockInResponse
import com.example.attendancesystem.data.model.response.ClockOutResponse
import com.example.attendancesystem.data.model.response.TodayAttendanceResponse
import com.example.attendancesystem.network.AttendanceApi
import com.example.attendancesystem.network.NetworkErrorMapper
import com.example.attendancesystem.network.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AttendanceRepository @Inject constructor(
    private val attendanceApi: AttendanceApi
) {

    suspend fun getTodayAttendance(): NetworkResult<TodayAttendanceResponse> {

        return try {

            val response = attendanceApi.getTodayAttendance()

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkErrorMapper.fromResponse(response)
            }
        } catch (e: Exception) {

            NetworkErrorMapper.fromException(e)
        }
    }

    suspend fun getHistory(): NetworkResult<List<AttendanceResponse>> {

        return try {

            val response = attendanceApi.attendanceHistory()

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkErrorMapper.fromResponse(response)
            }
        } catch (e: Exception) {

            NetworkErrorMapper.fromException(e)
        }
    }

    suspend fun clockIn(): NetworkResult<ClockInResponse> {

        return try {

            val response = attendanceApi.clockIn()

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkErrorMapper.fromResponse(response)
            }
        } catch (e: Exception) {

            NetworkErrorMapper.fromException(e)
        }
    }


    suspend fun clockOut(): NetworkResult<ClockOutResponse> {

        return try {
            val response = attendanceApi.clockOut()

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkErrorMapper.fromResponse(response)
            }

        } catch (e: Exception) {

            NetworkErrorMapper.fromException(e)

        }

    }

}
