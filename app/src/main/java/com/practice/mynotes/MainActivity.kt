package com.practice.mynotes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.practice.mynotes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NoteContract.View {
    lateinit var adapter: NotesAdapter
    lateinit var notePresenter: NotePresenter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteDAO = Database.getInstance(this).noteDAO()
        val repository = Repository(noteDAO)
        notePresenter = NotePresenter(repository, this)

        binding.IVAddNotes.setOnClickListener {
            var intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

        binding.IVSearch.setOnClickListener {
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        setupRecycler()

    }

    override fun onResume() {
        super.onResume()
        notePresenter.loadNotes()
    }

    override fun showNote(notes: LiveData<List<Notes>>) {
        notes.observe(this) { updatedNotes ->
         adapter.updateNotes(updatedNotes)

        }

    }

    override fun showMessage(message: String) {
        TODO("Not yet implemented")
    }

    private fun setupRecycler() {
        adapter = NotesAdapter(this, mutableListOf()) // Start with an empty list
        binding.RVMain.adapter = adapter
        binding.RVMain.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun onBackPressed() {
        exit()
    }

    private fun exit(){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Are you sure to exit?")
        alertDialog.setIcon(R.drawable.baseline_exit_to_app_24)
        alertDialog.setPositiveButton("yes") { _, _ ->
            finishAffinity()
        }
        alertDialog.setNegativeButton("No") { _, _ -> return@setNegativeButton }
        alertDialog.show()
    }

}
