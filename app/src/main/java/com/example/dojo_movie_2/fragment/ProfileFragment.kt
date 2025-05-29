package com.example.dojo_movie_2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dojo_movie_2.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // Dummy data sementara
        tvName.text = "Halo, Pengguna!"

        btnLogout.setOnClickListener {
            // Nantinya akan diarahkan ke halaman login
            activity?.finish() // menutup activity untuk simulasi logout
        }

        return view
    }
}
