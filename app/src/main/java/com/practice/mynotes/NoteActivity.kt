package com.practice.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import com.practice.mynotes.databinding.ActivityNoteViewBinding

class NoteActivity : AppCompatActivity(), NoteContract.View {
    private lateinit var notePresenter: NoteContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNoteViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val note: Notes? = intent.getParcelableExtra("NOTE_DATA")

        val noteDAO = Database.getInstance(this).noteDAO()
        val repository = Repository(noteDAO)
        notePresenter = NotePresenter(repository, this)


        if (note == null) {
            binding.IVShare.isEnabled = false
            binding.IVDelete.isEnabled = false
            binding.IVShare.alpha = 0.5f
            binding.IVDelete.alpha = 0.5f


        } else {
            binding.IVShare.isEnabled = true
            binding.IVDelete.isEnabled = true
            binding.IVShare.alpha = 1f
            binding.IVDelete.alpha = 1f
            binding.EDTSubject.setText(note.subject.toString().trim())
            binding.EDTBody.setText(note.description.toString().trim())
        }

        binding.IVDelete.setOnClickListener {
            if (note != null) {
                notePresenter.deleteNote(note)
            }
        }

        binding.IVShare.setOnClickListener {
            val stringTemp =
                "Title: ${note?.subject}, Description: ${note?.description}"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, stringTemp)
            startActivity(Intent.createChooser(intent, "Share via"))
        }

        binding.IVDelete.setOnClickListener {
            if (note != null) {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Are you sure?")
                alertDialog.setIcon(R.drawable.baseline_delete_24)
                alertDialog.setPositiveButton("yes") { _, _ ->
                    notePresenter.deleteNote(note)
                    finish()
                }
                alertDialog.setNegativeButton("No") { _, _ -> return@setNegativeButton }
                alertDialog.show()

            }
        }

        binding.IVBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {


            var subject = binding.EDTSubject.text.toString().trim()
            var body = binding.EDTBody.text.toString().trim()
            if (subject.isNotEmpty()) {
                var tempNote = Notes(subject = subject, description = body)

                if (note == null) {
                    notePresenter.insertNote(tempNote)
                }else {
                    tempNote.id=note.id
                    notePresenter.updateNote(tempNote)
                }
                Toast.makeText(
                    applicationContext,
                    "Note ${tempNote.subject} was added Successfully.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(this, "Subject cannot be empty.", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun showNote(notes: LiveData<List<Notes>>) {
        Log.e("NoteActivity", "note Shown")
    }

    override fun showMessage(message: String) {
        Log.e("NoteActivity", "note Added")
    }

}