package com.chs.jwt_auth_test.common

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val PREFERENCES_NAME: String = "jwt_pref"
    const val BASE_FAKE_API_URL: String = "https://fakeapi.platzi.co/api/v1"
    const val TOKEN_TYPE: String = "Bearer"
    val PREF_KEY_ACCESS_TOKEN: Preferences.Key<String> = stringPreferencesKey("key_access_token")
    val PREF_KEY_REFRESH_TOKEN: Preferences.Key<String> = stringPreferencesKey("key_refresh_token")

    const val TIME_CONNECTION: Long = 15L
    const val TIME_READ: Long = 15L
    const val TIME_WRITE: Long = 15L
}