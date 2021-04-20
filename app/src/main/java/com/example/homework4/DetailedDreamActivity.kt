package com.example.homework4

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class DetailedDreamActivity: AppCompatActivity() {
    private lateinit var textView_title: TextView
    private lateinit var textView_emotion: TextView
    private lateinit var textView_content: TextView
    private lateinit var textView_reflection: TextView
    private lateinit var button_delete: Button
    private lateinit var button_update: Button

    private val dreamViewModel:DreamViewModel by viewModels {
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)

        textView_title = findViewById(R.id.textViewTitle)
        textView_emotion = findViewById(R.id.textViewEmotion)
        textView_content = findViewById(R.id.textViewContent)
        textView_reflection = findViewById(R.id.textViewReflection)
        button_delete = findViewById(R.id.button_delete)
        button_update = findViewById(R.id.button_update)
        val id = intent.getIntExtra("id", 0)
        dreamViewModel.getDream(id).observe(this, Observer {
            dream -> dream?.let {
            textView_title.text = it.title.toUpperCase()
            textView_emotion.text = getString(R.string.emotion) + "\n" + it.emotion.toUpperCase()
            textView_content.text = getString(R.string.context) + "\n" + it.content.toUpperCase()
            textView_reflection.text = getString(R.string.reflection) + "\n" + it.reflection.toUpperCase()
        }
        })

        button_delete.setOnClickListener {
            onCreateDialog().show()
        }

        button_update.setOnClickListener {
            val intent = Intent(this@DetailedDreamActivity, UpdateDreamActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }


    }

    fun onCreateDialog(): Dialog {
        return this.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog)
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { _, _ ->
                        dreamViewModel.delete(intent.getIntExtra("id", 0))
                        finish()
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { _, _ ->
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}