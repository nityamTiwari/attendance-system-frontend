package com.nityam.attendancesystem.data.repository

import com.nityam.attendancesystem.data.model.response.AttendanceResponse
import com.nityam.attendancesystem.data.model.response.ClockInResponse
import com.nityam.attendancesystem.data.model.response.ClockOutResponse
import com.nityam.attendancesystem.data.model.response.TodayAttendanceResponse
import com.nityam.attendancesystem.network.AttendanceApi
import com.nityam.attendancesystem.network.NetworkErrorMapper
import com.nityam.attendancesystem.network.NetworkResult
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
