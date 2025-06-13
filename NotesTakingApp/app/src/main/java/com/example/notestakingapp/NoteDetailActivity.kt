package com.example.notestakingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NoteDetailActivity : AppCompatActivity() {
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val titleView = findViewById<TextView>(R.id.detailTitle)
        val contentView = findViewById<TextView>(R.id.detailContent)
        val dateView = findViewById<TextView>(R.id.detailDate)
        val editBtn = findViewById<Button>(R.id.btnEdit)

        // Receive data
        val title = intent.getStringExtra("note_title")
        val content = intent.getStringExtra("note_content")
        val date = intent.getStringExtra("note_date")
        noteId = intent.getIntExtra("note_id", -1)

        titleView.text = title
        contentView.text = content
        dateView.text = date

        editBtn.setOnClickListener {
            val intent = Intent(this, EditNoteActivity::class.java)
            intent.putExtra("note_id", noteId)
            intent.putExtra("note_title", title)
            intent.putExtra("note_content", content)
            startActivity(intent)
        }
    }
}
