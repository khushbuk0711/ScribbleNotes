package com.example.scribblenotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.scribblenotes.model.Note
import com.example.scribblenotes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(app:Application,private val noteRepository: NoteRepository) :AndroidViewModel(app){

    fun addNote(note:Note)=
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    fun deleteNote(note:Note)=
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
        }
    fun updateNote(note:Note)=
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.updateNote(note)
        }
    fun getAllNotes() : LiveData<List<Note>>{
        return noteRepository.gelAllNotes()
    }

    fun searchNote(query: String):LiveData<List<Note>> {
        return noteRepository.searchNote(query)
    }
}