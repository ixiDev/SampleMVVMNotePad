package com.example.samplemvvmnotepad.ui.main.createnote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samplemvvmnotepad.data.Repository
import com.example.samplemvvmnotepad.data.entities.Note
import java.util.*


class CreateNoteViewModel(private val repository: Repository) : ViewModel() {

    /**
     * this for save note color we need it later ok :D
     */
    private val noteColor = MutableLiveData("#FFFFFF")

    /**
     * get note from repository
     * @param noteId : note id not null
     */
    suspend fun getNoteById(noteId: Int): Note {

        return repository.findNoteById(noteId)
    }

    suspend fun saveNote(noteId: Int?, noteText: String) {

        if (noteId == null) {
            // save new note
            val createdDate = Date().time
            val newNote = Note(
                text = noteText,
                createdDate = createdDate,
                updatedDate = createdDate,
                color = noteColor.value!!
            )
            repository.saveNewNote(newNote)
        } else {
            // if id not null so update the old note

            val oldNote = getNoteById(noteId)
            oldNote.text = noteText
            oldNote.updatedDate = Date().time
            oldNote.color = noteColor.value!!

            repository.updateNote(oldNote)

        }

    }


}