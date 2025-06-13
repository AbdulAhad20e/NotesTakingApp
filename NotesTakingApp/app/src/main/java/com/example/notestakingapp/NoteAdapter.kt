package com.example.notestakingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notestakingapp.R

class NoteAdapter(
    private var notes: List<Note>,
    private val onItemClick: (Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var filteredNotes: List<Note> = notes

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textNoteTitle)
        val description: TextView = itemView.findViewById(R.id.textNoteDescription)
        val date: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }


    fun filter(query: String) {
        filteredNotes = if (query.isBlank()) {
            notes
        } else {
            notes.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.content.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = filteredNotes[position]
        holder.title.text = note.title
        holder.description.text = getShortContent(note.content)
        holder.date.text = note.getFormattedDate()

        holder.itemView.setOnClickListener{
            onItemClick(note)
        }
    }

    override fun getItemCount(): Int = filteredNotes.size

    fun updateList(newList: List<Note>) {
        notes = newList
        filteredNotes = newList
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note {
        return filteredNotes[position]
    }

    fun getShortContent(content:String):String
    {
        val words = content.trim().split("\\s+".toRegex())
        return if (words.size <= 3) {
            content.trim()
        } else {
            words.take(3).joinToString(" ") + "..."
        }
    }
}
