package com.example.dojo_movie_2.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dojo_movie_2.LoginActivity
import com.example.dojo_movie_2.MainActivity
import com.example.dojo_movie_2.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // Tampilkan nomor telepon dari session
        val prefs = requireActivity().getSharedPreferences(MainActivity.PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
        val phone = prefs.getString(MainActivity.KEY_PHONE, "Pengguna")
        tvName.text = "Halo, $phone!"

        btnLogout.setOnClickListener {
            // Hapus session
            prefs.edit().clear().apply()

            // Pindah ke LoginActivity, clear back stack supaya tidak bisa back ke MainActivity
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }

        return view
    }
}
