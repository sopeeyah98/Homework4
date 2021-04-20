package com.example.homework4

import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class AddDreamActivity: AppCompatActivity() {
    private lateinit var editText_title: EditText
    private lateinit var editText_content: EditText
    private lateinit var editText_reflection: EditText
    private lateinit var emotions_spinner: Spinner
    private lateinit var button_save: Button

    private val dreamViewModel: DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editText_title = findViewById(R.id.editText_title)
        editText_content = findViewById(R.id.editText_content)
        editText_reflection = findViewById(R.id.editText_reflection)
        emotions_spinner = findViewById(R.id.spinner)
        button_save = findViewById(R.id.button_save)

        button_save.setOnClickListener {
            if (TextUtils.isEmpty(editText_title.text) ||
                TextUtils.isEmpty(editText_content.text) ||
                TextUtils.isEmpty(editText_reflection.text)) {
                ToastError("Missing Input")
            } else {
                val dream = Dream(editText_title.text.toString(), editText_content.text.toString(),
                    editText_reflection.text.toString(), emotions_spinner.selectedItem.toString())
                dreamViewModel.insertDream(dream)
                finish()
            }
        }
    }

    private fun ToastError(text:String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}