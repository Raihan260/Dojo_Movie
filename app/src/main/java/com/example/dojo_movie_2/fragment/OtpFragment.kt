package com.example.dojo_movie_2.fragment.auth

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.dojo_movie_2.R

class OtpFragment : Fragment(R.layout.fragment_otp) {

    private val dummyOtp = "123456" // bisa diganti acak nanti

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etOtp = view.findViewById<EditText>(R.id.etOtp)
        val btnVerifyOtp = view.findViewById<Button>(R.id.btnVerifyOtp)

        btnVerifyOtp.setOnClickListener {
            val otpInput = etOtp.text.toString().trim()
            if (otpInput == dummyOtp) {
                Toast.makeText(requireContext(), "OTP berhasil diverifikasi", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .commit()
            } else {
                Toast.makeText(requireContext(), "OTP salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
