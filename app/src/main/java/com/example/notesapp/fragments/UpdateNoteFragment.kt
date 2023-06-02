@file:Suppress("DEPRECATION")

package com.example.notesapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentUpdateNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel


class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {


    private  var _binding : FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note
    // since the update note fragment contains arguments in nav_graph
    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).notesViewModel
        currentNote = args.note!!

        binding.etNoteBodyUpdate.setText(currentNote.noteContent)
        binding.etNoteTitleUpdate.setText(currentNote.noteTitle)

        // if user updates note, id of note stays the same
        binding.fabDone.setOnClickListener {
            val title = binding.etNoteTitleUpdate.toString().trim()
            //val body = binding.etNoteBodyUpdate.toString().trim()

            if(title.isNotEmpty()) {
                val note = Note(currentNote.id, currentNote.noteTitle, currentNote.noteContent)
                notesViewModel.updateNote(note)
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
            else {
                Toast.makeText(context, "please enter note title", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Delete anyway") {
                _,_ -> notesViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    @Deprecated("Deprecated")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }
    @Deprecated("Deprecated")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuDelete -> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}