// OtpActivity.kt - Activity untuk verifikasi OTP sederhana
package com.example.dojo_movie_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dojo_movie_2.db.DatabaseHelper

class OtpActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var otpEditText: EditText
    private lateinit var verifyButton: Button
    private var expectedOtp: String = ""
    private var phone: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        db = DatabaseHelper(this)

        otpEditText = findViewById(R.id.editOtp)
        verifyButton = findViewById(R.id.btnVerifyOtp)

        expectedOtp = intent.getStringExtra("otp") ?: ""
        phone = intent.getStringExtra("phone") ?: ""
        password = intent.getStringExtra("password") ?: ""

        verifyButton.setOnClickListener {
            val enteredOtp = otpEditText.text.toString()

            if (enteredOtp.isEmpty()) {
                Toast.makeText(this, "OTP must be filled", Toast.LENGTH_SHORT).show()
            } else if (enteredOtp != expectedOtp) {
                Toast.makeText(this, "Invalid OTP Code", Toast.LENGTH_SHORT).show()
            } else {
                db.addUser(phone, password)
                // Tambahkan setelah db.addUser(phone, password)
                val prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
                prefs.edit().putString(MainActivity.KEY_PHONE, phone).apply()
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}

