package com.example.samplemvvmnotepad.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvmnotepad.data.Repository
import com.example.samplemvvmnotepad.di.AppInjector
import com.example.samplemvvmnotepad.ui.main.createnote.CreateNoteViewModel
import com.example.samplemvvmnotepad.ui.main.noteslist.NotesListViewModel


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        // to create this viewmodels we need Repository
        // and to create repo we need a dao object
        val repository = Repository(
            AppInjector.getDao(context)
        )
        return when {

            modelClass.isAssignableFrom(CreateNoteViewModel::class.java) -> {
                CreateNoteViewModel(repository) as T  // here return new object of createNoteViewModel
            }

            modelClass.isAssignableFrom(NotesListViewModel::class.java) -> {
                NotesListViewModel(repository) as T // here return new object of NotesListViewModel
            }

            else -> {
                throw IllegalArgumentException("not allowed")
            }
        }

    }
}