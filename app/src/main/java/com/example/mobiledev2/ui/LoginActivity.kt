package com.example.mobiledev2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiledev2.R
import com.example.mobiledev2.data.local.SettingPreferences

class LoginActivity : AppCompatActivity() {

    private val viewModel: SettingViewModel by viewModels {
        ViewModelFactory(
            SettingPreferences.getInstance(this)
        )
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username =
            findViewById<EditText>(R.id.etUsername)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        // AUTO LOGIN
        viewModel.getUserSession()
            .observe(this) {

                if(it.first.isNotEmpty()) {

                    startActivity(
                        Intent(
                            this,
                            HomeActivity::class.java
                        )
                    )

                    finish()
                }
            }

        btnLogin.setOnClickListener {

            val name =
                username.text.toString()

            val token =
                "token_abc123xyz"

            viewModel.saveUserSession(
                token,
                name
            )

            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                )
            )

            finish()
        }
    }
}

