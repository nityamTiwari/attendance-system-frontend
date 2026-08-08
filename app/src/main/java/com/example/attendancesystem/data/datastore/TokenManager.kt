package com.example.attendancesystem.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.attendancesystem.common.Gender
import com.example.attendancesystem.data.model.LocalProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

        // Local profile cache - see LocalProfile.kt for why this exists instead of a real
        // network-backed profile.
        val FIRST_NAME = stringPreferencesKey("profile_first_name")
        val LAST_NAME = stringPreferencesKey("profile_last_name")
        val EMAIL = stringPreferencesKey("profile_email")
        val PHONE = stringPreferencesKey("profile_phone")
        val DOB = stringPreferencesKey("profile_dob")
        val GENDER = stringPreferencesKey("profile_gender")
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

    // --- Local profile cache ---

    val profile: Flow<LocalProfile> = context.dataStore.data.map { preferences ->
        LocalProfile(
            firstName = preferences[PreferencesKeys.FIRST_NAME],
            lastName = preferences[PreferencesKeys.LAST_NAME],
            email = preferences[PreferencesKeys.EMAIL],
            phone = preferences[PreferencesKeys.PHONE],
            dob = preferences[PreferencesKeys.DOB],
            gender = preferences[PreferencesKeys.GENDER]?.let { name ->
                runCatching { Gender.valueOf(name) }.getOrNull()
            }
        )
    }

    /** Called after a successful Register - we have the full set of fields the user typed in. */
    suspend fun saveProfile(profile: LocalProfile) {
        context.dataStore.edit { preferences ->
            profile.firstName?.let { preferences[PreferencesKeys.FIRST_NAME] = it }
            profile.lastName?.let { preferences[PreferencesKeys.LAST_NAME] = it }
            profile.email?.let { preferences[PreferencesKeys.EMAIL] = it }
            profile.phone?.let { preferences[PreferencesKeys.PHONE] = it }
            profile.dob?.let { preferences[PreferencesKeys.DOB] = it }
            profile.gender?.let { preferences[PreferencesKeys.GENDER] = it.name }
        }
    }

    /**
     * Called after a successful Login. Only fills in the email if there's no local profile
     * yet on this device (e.g. fresh install, logging into an existing account) - never
     * overwrites a fuller profile that Register already saved.
     */
    suspend fun saveEmailIfProfileMissing(email: String) {
        val existing = profile.first()
        if (existing.isEmpty) {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.EMAIL] = email
            }
        }
    }

    suspend fun clearProfile() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.FIRST_NAME)
            preferences.remove(PreferencesKeys.LAST_NAME)
            preferences.remove(PreferencesKeys.EMAIL)
            preferences.remove(PreferencesKeys.PHONE)
            preferences.remove(PreferencesKeys.DOB)
            preferences.remove(PreferencesKeys.GENDER)
        }
    }
}
