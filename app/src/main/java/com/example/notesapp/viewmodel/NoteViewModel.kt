package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository)
    : AndroidViewModel(app)
{
    //make use of repository functions
    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    // no coroutine launch for functions that are not suspend functions
    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNotes(query: String?) = noteRepository.searchNotes(query)

}