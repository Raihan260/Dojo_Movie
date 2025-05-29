package com.example.dojo_movie_2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dojo_movie_2.R
import com.example.dojo_movie_2.model.Film
import android.content.Intent
import com.example.dojo_movie_2.DetailFilmActivity


import com.bumptech.glide.Glide

class FilmAdapter(private val filmList: List<Film>) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFilm: ImageView = itemView.findViewById(R.id.imgFilm)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = filmList[position]
        holder.tvTitle.text = film.title
        holder.tvPrice.text = "Rp ${film.price}"

        // Load img berdasarkan ID
        val imageUrl = getImageUrlById(film.id)
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imgFilm)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailFilmActivity::class.java)
            intent.putExtra("title", film.title)
            intent.putExtra("price", film.price)
            intent.putExtra("image", imageUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = filmList.size

    // Mapping ID -> URL
    private fun getImageUrlById(id: String): String {
        return when (id) {
            "MV001" -> "https://static.wikia.nocookie.net/kaijuverse/images/6/6f/Godzilla-vs-Kong-Kongzilla.jpg/revision/latest?cb=20220429182054" // Kongzilla
            "MV002" -> "https://i.pinimg.com/736x/d3/b0/bd/d3b0bdc3f70fad31c8046b1a67594c6a.jpg" // Final Fantalion
            "MV003" -> "https://static.wikia.nocookie.net/jamesbond/images/d/dc/James_Bond_%28Pierce_Brosnan%29_-_Profile.jpg/revision/latest?cb=20220207082851" // Bond Jampshoot
            else -> "https://via.placeholder.com/150" // Default image
        }
    }
}

