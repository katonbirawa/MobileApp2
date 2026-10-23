package com.example.mobiledev2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mobiledev2.R
import com.example.mobiledev2.data.local.SettingPreferences
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private val viewModel: SettingViewModel by viewModels {
        ViewModelFactory(
            SettingPreferences.getInstance(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchTheme =
            findViewById<SwitchMaterial>(R.id.switchTheme)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->

            switchTheme.isChecked = isDarkModeActive

            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            } else {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }
    }
}
