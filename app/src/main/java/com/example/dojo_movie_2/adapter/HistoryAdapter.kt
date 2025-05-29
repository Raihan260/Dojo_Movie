package com.example.dojo_movie_2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dojo_movie_2.R
import com.example.dojo_movie_2.model.HistoryModel

class HistoryAdapter(private val historyList: List<HistoryModel>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFilm: ImageView = itemView.findViewById(R.id.imgHistoryFilm)
        val tvTitle: TextView = itemView.findViewById(R.id.tvHistoryTitle)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvHistoryQty)
        val tvPrice: TextView = itemView.findViewById(R.id.tvHistoryPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]

        holder.tvTitle.text = history.title
        holder.tvQuantity.text = "Jumlah: ${history.quantity} tiket"
        holder.tvPrice.text = "Total Harga: Rp ${history.price}"

        // Load img berdasarkan judul film
        val imageUrl = when (history.title) {
            "Kongzilla" -> "https://static.wikia.nocookie.net/kaijuverse/images/6/6f/Godzilla-vs-Kong-Kongzilla.jpg/revision/latest?cb=20220429182054"
            "Final Fantalion" -> "https://i.pinimg.com/736x/d3/b0/bd/d3b0bdc3f70fad31c8046b1a67594c6a.jpg"
            "Bond Jampshoot" -> "https://static.wikia.nocookie.net/jamesbond/images/d/dc/James_Bond_%28Pierce_Brosnan%29_-_Profile.jpg/revision/latest?cb=20220207082851"
            else -> "" // kosong jika tidak ada
        }

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imgFilm)
    }

    override fun getItemCount(): Int = historyList.size
}
