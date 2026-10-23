package com.example.mobiledev2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobiledev2.data.local.SettingPreferences

class ViewModelFactory(
    private val pref: SettingPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                SettingViewModel::class.java
            )
        ) {
            return SettingViewModel(pref) as T
        }

        throw IllegalArgumentException()
    }
}

