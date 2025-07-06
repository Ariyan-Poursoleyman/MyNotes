package com.practice.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.practice.mynotes.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity(),NoteContract.View {
    lateinit var notePresenter: NotePresenter
    lateinit var adapter: NotesAdapter
    private var searchList: MutableList<Notes> = mutableListOf()
    lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val noteDAO = Database.getInstance(this).noteDAO()
        val repository = Repository(noteDAO)
        notePresenter = NotePresenter(repository,this)

        setupRecyclerView()

        binding.IVSearch.setOnClickListener {
        var text = binding.EDTSearch.text.toString().trim()

            performSearch()

        }
        binding.EDTSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {

                performSearch()  // Call the search function
                true  // Return true to consume the event
            } else {
                false  // Return false to let the system handle other actions
            }
        }
        binding.IVBack.setOnClickListener {
            finish()
        }

    }

    override fun showNote(notes: LiveData<List<Notes>>) {
        notes.observe(this, Observer { results ->
            searchList.clear()
            searchList.addAll(results)
            adapter.notifyDataSetChanged()
        })    }

    override fun showMessage(message: String) {
        TODO("Not yet implemented")
    }
    private fun setupRecyclerView() {
        adapter = NotesAdapter(this, searchList)  // Pass empty list initially
        binding.RVSearchResult.adapter = adapter
        binding.RVSearchResult.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }
    private fun performSearch() {
        val text = binding.EDTSearch.text.toString().trim()
        if (text.isNotEmpty()) {
            notePresenter.search(text).observe(this, Observer { results ->
                searchList.clear()
                searchList.addAll(results)
                adapter.notifyDataSetChanged()  // Refresh adapter
            })
        }
    }

}