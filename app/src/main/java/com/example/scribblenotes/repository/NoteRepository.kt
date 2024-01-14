package com.example.scribblenotes.repository

import com.example.scribblenotes.database.NoteDatabase
import com.example.scribblenotes.model.Note

class NoteRepository(private val db:NoteDatabase) {
    suspend fun insertNote(note: Note)= db.getNoteDao().insertNote(note);
    suspend fun updateNote(note: Note)= db.getNoteDao().updateNote(note);
    suspend fun deleteNote(note: Note)= db.getNoteDao().deleteNote(note);

    fun gelAllNotes()= db.getNoteDao().getAllNotes();
    fun searchNote(query: String)=db.getNoteDao().searchNote(query);
}