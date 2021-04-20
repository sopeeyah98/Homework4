package com.example.homework4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var button_add: Button

    private val dreamViewModel: DreamViewModel by viewModels {
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        button_add = findViewById(R.id.button_add)

        val adapter = DreamListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        button_add.setOnClickListener {
            var intent = Intent(this@MainActivity, AddDreamActivity::class.java)
            startActivity(intent)
        }

        dreamViewModel.allDreams.observe(this, Observer {
            dreams -> dreams?.let {
                adapter.submitList(it)
        }
        })
    }
}