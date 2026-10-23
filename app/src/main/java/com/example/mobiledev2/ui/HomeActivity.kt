package com.example.mobiledev2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mobiledev2.R
import com.example.mobiledev2.data.local.SettingPreferences

class HomeActivity : AppCompatActivity() {

    private val viewModel: SettingViewModel by viewModels {
        ViewModelFactory(
            SettingPreferences.getInstance(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvUser = findViewById<TextView>(R.id.tvUser)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnSetting = findViewById<Button>(R.id.btnSetting)

        // Mengamati mode tema (Gelap/Terang)
        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
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

        // Mengamati sesi pengguna untuk menampilkan pesan selamat datang
        viewModel.getUserSession().observe(this) { (_, name) ->
            val username = name.ifEmpty { "User" }
            tvUser.text = getString(R.string.welcome_user, username)
        }

        btnSetting.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }

        btnLogout.setOnClickListener {
            viewModel.logout()
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
            finish()
        }
    }
}
