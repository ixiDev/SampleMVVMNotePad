package com.example.samplemvvmnotepad.ui.main.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.samplemvvmnotepad.data.Repository
import com.example.samplemvvmnotepad.data.entities.Note

class NotesListViewModel(private val repository: Repository) : ViewModel() {


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

}