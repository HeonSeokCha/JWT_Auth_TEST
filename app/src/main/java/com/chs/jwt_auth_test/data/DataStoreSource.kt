package com.chs.jwt_auth_test.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreSource (
    private val dataStore: DataStore<Preferences>
) {
    suspend fun <T>putData(
        keyName: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { preference ->
            preference[keyName] = value
        }
    }

    suspend fun <T>getData(
        keyName: Preferences.Key<T>,
        defaultValue: T
    ): T = dataStore.data
        .catch { e ->
            emit(emptyPreferences())
        }.map { preferences ->
           preferences[keyName] ?: defaultValue
        }.first()

    suspend fun <T>deleteData(keyName: Preferences.Key<T>) {
        dataStore.edit { it.remove(keyName) }
    }
}