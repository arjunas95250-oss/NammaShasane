package com.mindmatrix.nammashasane.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mindmatrix.nammashasane.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            when {
                name.isEmpty() -> {
                    Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                }
                email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                }
                password.length < 6 -> {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                }
                password != confirmPassword -> {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Save credentials
                    val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
                    prefs.edit()
                        .putString("name", name)
                        .putString("email", email)
                        .putString("password", password)
                        .putBoolean("isLoggedIn", true)
                        .apply()

                    Toast.makeText(this, "Account created! Welcome, $name!", Toast.LENGTH_SHORT).show()

                    // Go to main app
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            finish() // Go back to login
        }
    }
}