package com.bangkit.skutapplication.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.skutapplication.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[USER_NAME] ?: "",
                preferences[EMAIL]?: "",
                preferences[PASSWORD] ?: "",
                preferences[TOKEN] ?: "",
                preferences[CALENDAR] ?: ""
            )
        }
    }

    suspend fun login(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = ""
            preferences[EMAIL] = ""
            preferences[PASSWORD] = ""
            preferences[TOKEN] = ""
            preferences[CALENDAR] = ""
        }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = username
        }
    }

    suspend fun setFirstCalendar(){
        dataStore.edit { prefences ->
            prefences[CALENDAR] = "set"
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val USER_NAME = stringPreferencesKey("username")
        private val EMAIL = stringPreferencesKey("email")
        private val PASSWORD = stringPreferencesKey("password")
        private val TOKEN = stringPreferencesKey("token")
        private val CALENDAR = stringPreferencesKey("calendar")
        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}