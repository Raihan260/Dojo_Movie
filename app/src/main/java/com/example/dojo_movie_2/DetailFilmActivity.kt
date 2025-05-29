package com.example.dojo_movie_2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dojo_movie_2.db.DatabaseHelper
import com.example.dojo_movie_2.model.HistoryModel

class DetailFilmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        val title = intent.getStringExtra("title") ?: "Unknown Title"
        val price = intent.getIntExtra("price", 0)
        val imageUrl = intent.getStringExtra("image") ?: ""

        val tvTitle: TextView = findViewById(R.id.tvDetailTitle)
        val tvPrice: TextView = findViewById(R.id.tvDetailPrice)
        val imgFilm: ImageView = findViewById(R.id.imgDetailFilm)
        val etQuantity: EditText = findViewById(R.id.etQuantity)
        val tvTotalPrice: TextView = findViewById(R.id.tvTotalPrice)
        val btnBeli: Button = findViewById(R.id.btnBeli)
        val btnBack: ImageButton = findViewById(R.id.btnBack)

        val db = DatabaseHelper(this)

        tvTitle.text = title
        tvPrice.text = "Rp $price"

        if (imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgFilm)
        } else {
            imgFilm.setImageResource(R.drawable.ic_launcher_background)
        }

        // Tombol back
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Hitung total harga otomatis saat quantity berubah
        etQuantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val qty = s.toString().toIntOrNull() ?: 0
                val total = qty * price
                tvTotalPrice.text = "Total: Rp $total"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnBeli.setOnClickListener {
            val qtyStr = etQuantity.text.toString()

            if (qtyStr.isEmpty()) {
                Toast.makeText(this, "Jumlah tiket harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quantity = qtyStr.toIntOrNull()
            if (quantity == null || quantity <= 0) {
                Toast.makeText(this, "Jumlah tiket harus berupa angka lebih dari 0!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = db.insertHistory(
                HistoryModel(
                    title = title,            // judul film
                    price = price * quantity, // total harga
                    quantity = quantity       // jumlah tiket

                )
            )

            if (result != -1L) {
                Toast.makeText(this, "Pembelian berhasil disimpan!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Gagal menyimpan data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
