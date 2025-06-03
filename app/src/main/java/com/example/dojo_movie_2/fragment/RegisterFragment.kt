package com.example.dojo_movie_2.fragment.auth

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.dojo_movie_2.R
import com.example.dojo_movie_2.db.DatabaseHelper

class RegisterFragment : Fragment(R.layout.fragment_register) {

    companion object {
        var registeredEmail: String? = null // untuk disimpan sementara ke OTP
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val tvToLogin = view.findViewById<TextView>(R.id.tvToLogin)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Isi semua field!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = DatabaseHelper(requireContext())
            if (db.insertUser(email, password)) {
                registeredEmail = email
                Toast.makeText(requireContext(), "Register berhasil, verifikasi OTP", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, OtpFragment())
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
            }
        }

        tvToLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }
    }
}
