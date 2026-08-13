package com.nityam.attendancesystem.network

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager @Inject constructor() {

    private val _sessionExpired = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val sessionExpired: SharedFlow<Unit> = _sessionExpired.asSharedFlow()

    fun notifySessionExpired() {
        _sessionExpired.tryEmit(Unit)
    }
}
