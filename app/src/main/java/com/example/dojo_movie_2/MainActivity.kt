package com.example.dojo_movie_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dojo_movie_2.databinding.ActivityMainBinding
import com.example.dojo_movie_2.fragment.HistoryFragment
import com.example.dojo_movie_2.fragment.HomeFragment
import com.example.dojo_movie_2.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "user_session"
        const val KEY_PHONE = "phone"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek apakah user sudah login
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val phone = prefs.getString(KEY_PHONE, null)
        if (phone == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_history -> loadFragment(HistoryFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
