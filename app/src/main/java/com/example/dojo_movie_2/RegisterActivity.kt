// RegisterActivity.kt - Activity untuk registrasi user
package com.example.dojo_movie_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dojo_movie_2.db.DatabaseHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var toLoginText: TextView
    private var otpCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = DatabaseHelper(this)

        phoneEditText = findViewById(R.id.editPhone)
        passwordEditText = findViewById(R.id.editPassword)
        confirmPasswordEditText = findViewById(R.id.editConfirmPassword)
        registerButton = findViewById(R.id.btnRegister)
        toLoginText = findViewById(R.id.txtToLogin)

        registerButton.setOnClickListener {
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            when {
                phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ->
                    Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
                db.isPhoneExists(phone) ->
                    Toast.makeText(this, "Phone already registered", Toast.LENGTH_SHORT).show()
                password.length < 8 ->
                    Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                password != confirmPassword ->
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                else -> {
                    otpCode = (100000..999999).random().toString()
                    // Simulasi kirim OTP via SMS (bisa disimpan untuk validasi)
                    Toast.makeText(this, "OTP Code: $otpCode", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, OtpActivity::class.java)
                    intent.putExtra("otp", otpCode)
                    intent.putExtra("phone", phone)
                    intent.putExtra("password", password)
                    startActivity(intent)
                }
            }
        }

        toLoginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
