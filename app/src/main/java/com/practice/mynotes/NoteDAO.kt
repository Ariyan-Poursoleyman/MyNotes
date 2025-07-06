package com.practice.mynotes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {

    @Query("Select * from tbl_notes")
    fun getAllNotes(): LiveData<List<Notes>>

    @Insert
    fun insertNote(note : Notes) : Long

    @Delete
    fun deleteNote(note : Notes)  : Int

    @Update
    fun updateNote (note : Notes) : Int

    @Query("SELECT * FROM tbl_notes WHERE subject LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchNote(query: String): LiveData<List<Notes>>

}