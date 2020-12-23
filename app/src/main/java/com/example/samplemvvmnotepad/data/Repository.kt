package com.example.samplemvvmnotepad.data

import androidx.paging.PagingSource
import com.example.samplemvvmnotepad.data.db.NotesDao
import com.example.samplemvvmnotepad.data.entities.Note

// here we will use all logic between db and ViewModels
class Repository(private val dao: NotesDao) {


    suspend fun findNoteById(noteId: Int): Note {
        return this.dao.findNoteById(noteId)
    }

    suspend fun saveNewNote(newNote: Note) {
        dao.insert(newNote)
    }

    suspend fun updateNote(oldNote: Note) {
        dao.update(oldNote)
    }

    fun getAllNotes():PagingSource<Int,Note>{
        return dao.findAllNotes()
    }


}