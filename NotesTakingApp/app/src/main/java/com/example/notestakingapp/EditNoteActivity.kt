package com.example.notestakingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class EditNoteActivity : AppCompatActivity() {
    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText
    private lateinit var saveBtn: Button
    private lateinit var viewModel: NoteViewModel
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        editTitle = findViewById(R.id.editTitle)
        editContent = findViewById(R.id.editContent)
        saveBtn = findViewById(R.id.btnSave)

        // Setup ViewModel
        val dao = AppDatabase.getDatabase(application).noteDao()
        val repo = NoteRepository(dao)
        viewModel = ViewModelProvider(this, NoteViewModelFactory(repo))[NoteViewModel::class.java]

        // Get data from intent
        val title = intent.getStringExtra("note_title")
        val content = intent.getStringExtra("note_content")
        noteId = intent.getIntExtra("note_id", -1)

        // Fill fields
        editTitle.setText(title)
        editContent.setText(content)

        saveBtn.setOnClickListener {
            val newTitle = editTitle.text.toString()
            val newContent = editContent.text.toString()

            if (newTitle.isBlank() || newContent.isBlank()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedNote = Note(
                id = noteId,
                title = newTitle,
                content = newContent,
                updatedAt = System.currentTimeMillis()
            )
            viewModel.update(updatedNote) // uses REPLACE
            Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()

        }
    }
}
