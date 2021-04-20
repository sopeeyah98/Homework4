package com.example.homework4

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer


class UpdateDreamActivity: AppCompatActivity() {
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
        val id = intent.getIntExtra("id", 0)

        dreamViewModel.getDream(id).observe(this, Observer { dream ->
            dream?.let {
                editText_title.text = it.title.toUpperCase().toEditable()
                editText_content.text = it.content.toUpperCase().toEditable()
                editText_reflection.text = it.reflection.toUpperCase().toEditable()

                for (i in 0 until emotions_spinner.getCount()) {
                    if (emotions_spinner.getItemAtPosition(i).toString().equals(it.emotion)) {
                        emotions_spinner.setSelection(i)
                        break
                    }
                }
            }
        })

        button_save.setOnClickListener {
            if (TextUtils.isEmpty(editText_title.text) ||
                TextUtils.isEmpty(editText_content.text) ||
                TextUtils.isEmpty(editText_reflection.text)) {
                ToastError("Missing Input")
            } else {
                dreamViewModel.updateDream(editText_title.text.toString(),
                                            editText_content.text.toString(),
                                            editText_reflection.text.toString(),
                                            emotions_spinner.selectedItem.toString(),
                                            id)
                finish()
            }
        }
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun ToastError(text:String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}