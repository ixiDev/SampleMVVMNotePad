package com.example.samplemvvmnotepad.ui.main.noteslist


import androidx.collection.ArraySet
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.samplemvvmnotepad.data.Repository
import com.example.samplemvvmnotepad.data.entities.Note
import kotlinx.coroutines.launch

class NotesListViewModel
@ViewModelInject constructor(private val repository: Repository) : ViewModel() {


    private val selectedNotesIds = ArraySet<Int>()

    /**
     * get all notes from db as pager
     */
    fun getAllNotes(): LiveData<PagingData<Note>> {

        val pager = Pager(
            config = PagingConfig(10, enablePlaceholders = false), // 10 notes per page
            pagingSourceFactory = {
                repository.getAllNotes()
            }
        )
        return pager.liveData
    }

    fun deleteSelectedNotes() {
        viewModelScope.launch {
            repository.deleteNotesByIds(selectedNotesIds)
            selectedNotesIds.clear() // clear saved ids
        }
    }

    fun setNoteSelected(isSelected: Boolean, noteId: Int) {
        if (isSelected) {
            selectedNotesIds.add(noteId)
        } else {
            selectedNotesIds.remove(noteId)
        }
    }

    fun getNotesCount(): LiveData<Int> {

        return repository.getNotesCount()
    }

}