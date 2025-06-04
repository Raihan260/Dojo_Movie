package com.example.dojo_movie_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dojo_movie_2.db.DatabaseHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var toRegisterText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = DatabaseHelper(this)

        phoneEditText = findViewById(R.id.editPhone)
        passwordEditText = findViewById(R.id.editPassword)
        loginButton = findViewById(R.id.btnLogin)
        toRegisterText = findViewById(R.id.txtToRegister)

        loginButton.setOnClickListener {
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Phone number and password must be filled", Toast.LENGTH_SHORT).show()
            } else if (!db.checkUser(phone, password)) {
                Toast.makeText(this, "Invalid phone number or password", Toast.LENGTH_SHORT).show()
            } else {
                // Simpan session login
                val prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
                prefs.edit().putString(MainActivity.KEY_PHONE, phone).apply()

                // Langsung ke MainActivity (bisa dilewatkan OTP jika perlu)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        toRegisterText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
