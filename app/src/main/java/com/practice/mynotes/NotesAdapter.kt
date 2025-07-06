package com.practice.mynotes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private val context: Context, private var notesList: MutableList<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById<TextView>(R.id.TVSubject)
        var description = itemView.findViewById<TextView>(R.id.TVDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.notes, parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.title.text = notesList[position].subject
        holder.description.text = notesList[position].description.toString()

        var tempNote = Notes(id = notesList[position].id,subject = notesList[position].subject, description = notesList[position].description)

        fun sendNote(note : Notes){
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra("NOTE_DATA", tempNote)
            context.startActivity(intent)

        }
        holder.title.setOnClickListener {
            sendNote(tempNote)
        }
        holder.description.setOnClickListener {
            sendNote(tempNote)
        }



    }

    override fun getItemCount(): Int {
        return notesList.size
    }
    fun updateNotes(newNotes: List<Notes>) {
        notesList.clear()
        notesList.addAll(newNotes)
        notifyDataSetChanged()
    }
}