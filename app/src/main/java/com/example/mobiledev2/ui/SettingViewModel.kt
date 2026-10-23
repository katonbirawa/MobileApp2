package com.example.mobiledev2.ui

import androidx.lifecycle.*
import com.example.mobiledev2.data.local.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(
    private val pref: SettingPreferences
) : ViewModel() {

    fun getThemeSettings() =
        pref.getThemeSetting().asLiveData()

    fun getUserSession() =
        pref.getUserSession().asLiveData()

    fun saveThemeSetting(
        isDarkModeActive: Boolean
    ) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun saveUserSession(
        token: String,
        name: String
    ) {
        viewModelScope.launch {
            pref.saveUserSession(token, name)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.clearUserSession()
        }
    }
}
