package com.example.dojo_movie_2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dojo_movie_2.R
import com.example.dojo_movie_2.adapter.HistoryAdapter
import com.example.dojo_movie_2.db.DatabaseHelper

class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recyclerView = view.findViewById(R.id.recyclerHistory)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = DatabaseHelper(requireContext())
        val data = db.getAllHistory()
        historyAdapter = HistoryAdapter(data)
        recyclerView.adapter = historyAdapter

        return view
    }
}
