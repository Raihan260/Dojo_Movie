package com.example.dojo_movie_2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dojo_movie_2.adapter.FilmAdapter
import com.example.dojo_movie_2.databinding.FragmentHomeBinding
import com.example.dojo_movie_2.model.Film
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: FilmAdapter
    private val filmList = mutableListOf<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FilmAdapter(filmList)
        binding.rvFilm.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilm.adapter = adapter
        fetchData()
    }



    private fun fetchData() {
        val url = "https://api.npoint.io/66cce8acb8f366d2a508"
        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    filmList.clear()
                    for (i in 0 until response.length()) {
                        val obj = response.getJSONObject(i)
                        val film = Film(
                            id = obj.getString("id"),
                            title = obj.getString("title"),
                            price = obj.getInt("price"),
                            image = obj.getString("image")
                        )
                        filmList.add(film)
                    }
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })

        Volley.newRequestQueue(requireContext()).add(request)
    }
}
