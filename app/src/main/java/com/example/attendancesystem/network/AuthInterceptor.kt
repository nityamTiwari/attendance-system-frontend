package com.example.attendancesystem.network

import com.example.attendancesystem.data.datastore.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking {
            tokenManager.token.first()
        }

        val request = chain.request()

        val newRequest =
            if(!token.isNullOrBlank()){

                request.newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer $token"

                    ).build()
            } else {
                request
            }
   return chain.proceed(newRequest)

    }


}