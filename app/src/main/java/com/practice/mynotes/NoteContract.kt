package com.practice.mynotes

import androidx.lifecycle.LiveData

interface NoteContract {

    interface View {
        fun showNote(notes: LiveData<List<Notes>>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun insertNote(note: Notes)
        fun deleteNote(note: Notes)
        fun updateNote(note: Notes)
        fun getAllNotes()
        fun search(query: String): LiveData<List<Notes>>

    }
}