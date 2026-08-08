package com.example.attendancesystem.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(
        name = "attendance_preferences"
    )

    private object PreferencesKeys {
        val JWT_TOKEN = stringPreferencesKey("jwt_token")

    }

    val token: Flow<String?> = context.dataStore.data.map { preferences ->

            preferences[PreferencesKeys.JWT_TOKEN]

        }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.JWT_TOKEN)
        }
    }

        suspend fun saveToken(token : String) {
            Log.d("Login", "Saving Token = $token")

            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.JWT_TOKEN] = token
            }

        }
}
