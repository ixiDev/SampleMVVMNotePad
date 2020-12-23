package com.example.samplemvvmnotepad.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samplemvvmnotepad.data.entities.Note


@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // add dao abstract fun

    abstract fun getNotesDao(): NotesDao
}