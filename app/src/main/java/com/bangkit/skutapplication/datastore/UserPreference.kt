package com.bangkit.skutapplication.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.skutapplication.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.URI

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[USER_NAME] ?: "",
                preferences[EMAIL]?: "",
                preferences[PASSWORD] ?: "",
                preferences[TOKEN] ?: "",
            preferences[IMG] ?: ""
            )
        }
    }

//    suspend fun login(loginResult: LoginResult) {
//        dataStore.edit { preferences ->
//            preferences[NAME_KEY] = loginResult.name
//            preferences[TOKEN_KEY] = loginResult.token
//            preferences[STATE_KEY] = true
//        }
//    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = ""
            preferences[EMAIL] = ""
            preferences[PASSWORD] = ""
            preferences[TOKEN] = ""
            preferences[IMG] = ""
        }
    }

    suspend fun saveUsername(username: String, image: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = username
            preferences[IMG] =  image
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val USER_NAME = stringPreferencesKey("username")
        private val EMAIL = stringPreferencesKey("email")
        private val PASSWORD = stringPreferencesKey("password")
        private val TOKEN = stringPreferencesKey("token")
        private val IMG = stringPreferencesKey("image")
        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}