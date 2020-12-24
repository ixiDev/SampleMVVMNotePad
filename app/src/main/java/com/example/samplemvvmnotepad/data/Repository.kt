package com.example.samplemvvmnotepad.data

import androidx.collection.ArraySet
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.example.samplemvvmnotepad.data.db.NotesDao
import com.example.samplemvvmnotepad.data.entities.Note
import javax.inject.Inject

// here we will use all logic between db and ViewModels

class Repository @Inject constructor(private val dao: NotesDao) {


    suspend fun findNoteById(noteId: Int): Note {
        return this.dao.findNoteById(noteId)
    }

    suspend fun saveNewNote(newNote: Note) {
        dao.insert(newNote)
    }

    suspend fun updateNote(oldNote: Note) {
        dao.update(oldNote)
    }

    fun getAllNotes(): PagingSource<Int, Note> {
        return dao.findAllNotes()
    }

    suspend fun deleteNotesByIds(notesIds: ArraySet<Int>) {
        notesIds.forEach { noteId ->
            dao.deleteNoteById(noteId)
        }
    }

    fun getNotesCount(): LiveData<Int> {
        return dao.getNotesCount()
    }


}