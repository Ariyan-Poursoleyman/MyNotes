package com.practice.mynotes

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class NotePresenter(private val repository: Repository, private var view: NoteContract.View?) :
    NoteContract.Presenter {

    private val presenterJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + presenterJob)

    override fun attachView(view: NoteContract.View) {
        this.view = view

    }

    override fun detachView() {
        presenterJob.cancel()
        view = null

    }

    override fun insertNote(note: Notes) {
        uiScope.launch {

            try {
                withContext(Dispatchers.IO) {
                    repository.insertNote(note)
                }
                view?.showMessage("${note.subject} Added")
            } catch (e: Exception) {
                view?.showMessage("Error happened : $e")
            }
        }

    }

    override fun deleteNote(note: Notes) {
        uiScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.deleteNote(note)
                }
                view?.showMessage("${note.subject} deleted")
            } catch (e: Exception) {
                view?.showMessage("Error happened : $e")
            }

        }

    }

    override fun updateNote(note: Notes) {
        uiScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.updateNote(note)
                }
                view?.showMessage("${note.subject} updated")
            } catch (e: Exception) {
                view?.showMessage("Error happened : $e")
            }
        }

    }

    override fun getAllNotes() {
        uiScope.launch {
            try {
                val notes = withContext(Dispatchers.IO) {
                    repository.getAllNotes()
                }
                view?.showNote(notes)
            } catch (e: Exception) {
                view?.showMessage("Error happened : $e")
            }

        }

    }

    override fun search(query: String): LiveData<List<Notes>> {
        return repository.searchNote(query)
    }
    fun loadNotes() {
        val notes = repository.getAllNotes()
        view?.showNote(notes)
    }

}