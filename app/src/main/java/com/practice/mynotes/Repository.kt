package com.practice.mynotes

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val notesDAO: NoteDAO) {


    suspend fun insertNote(note: Notes): Result<Long> = withContext(Dispatchers.IO) {
        try {
            val id  = notesDAO.insertNote(note)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteNote(note: Notes) : Result<Int> = withContext(Dispatchers.IO) {
        try{
            val rowDeleted = notesDAO.deleteNote(note)
            if(rowDeleted > 0){
                Result.success(rowDeleted)
            }else{
                Result.failure(Exception("Note note found!"))
            }
        }catch (e : Exception){
            Result.failure(e)
        }
    }

    fun getAllNotes(): LiveData<List<Notes>> {
        return notesDAO.getAllNotes()
    }

    suspend fun updateNote(note: Notes) : Result<Int> = withContext(Dispatchers.IO) {
        try {
            val rowsUpdated = notesDAO.updateNote(note)
            if (rowsUpdated > 0) {
                Result.success(rowsUpdated)
            } else {
                Result.failure(Exception("No note found to update!"))
            }
        } catch(e : Exception) {
            Result.failure(e)
        }
    }


    fun searchNote(query: String): LiveData<List<Notes>> {
        return notesDAO.searchNote(query)
    }

}