package com.example.mobiledev2.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val THEME_KEY =
        booleanPreferencesKey("theme_setting")

    private val USER_TOKEN_KEY =
        stringPreferencesKey("user_token")

    private val USER_NAME_KEY =
        stringPreferencesKey("user_name")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map {
            it[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(
        isDarkModeActive: Boolean
    ) {
        dataStore.edit {
            it[THEME_KEY] = isDarkModeActive
        }
    }

    fun getUserSession(): Flow<Pair<String, String>> {
        return dataStore.data.map {

            val token =
                it[USER_TOKEN_KEY] ?: ""

            val name =
                it[USER_NAME_KEY] ?: ""

            Pair(token, name)
        }
    }

    suspend fun saveUserSession(
        token: String,
        name: String
    ) {
        dataStore.edit {
            it[USER_TOKEN_KEY] = token
            it[USER_NAME_KEY] = name
        }
    }

    suspend fun clearUserSession() {
        dataStore.edit {
            it.remove(USER_TOKEN_KEY)
            it.remove(USER_NAME_KEY)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE:
                SettingPreferences? = null

        fun getInstance(
            context: Context
        ): SettingPreferences {

            return INSTANCE ?: synchronized(this) {

                val instance =
                    SettingPreferences(context.dataStore)

                INSTANCE = instance

                instance
            }
        }
    }
}