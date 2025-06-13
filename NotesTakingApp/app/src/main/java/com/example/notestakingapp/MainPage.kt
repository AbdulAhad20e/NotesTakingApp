package com.example.notestakingapp


import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton


class MainPage : AppCompatActivity() {
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val dateFormat = DateFormat.getDateFormat(
            applicationContext
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        val noteDao = AppDatabase.getDatabase(application).noteDao()
        val repository = NoteRepository(noteDao)
        viewModel = ViewModelProvider(this, NoteViewModelFactory(repository))[NoteViewModel::class.java]



        val editTitle = findViewById<EditText>(R.id.editTextTitle)
        val editNote = findViewById<EditText>(R.id.editTextNote)
        val saveBtn = findViewById<Button>(R.id.save)


        saveBtn.setOnClickListener {
            val theTitle = editTitle.text.toString()
            val content = editNote.text.toString()
            if (theTitle.isNotBlank() && content.isNotBlank()) {
                viewModel.insert(Note(title = theTitle, content = content))
                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}