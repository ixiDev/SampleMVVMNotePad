package com.example.samplemvvmnotepad.di

import android.content.Context
import androidx.room.Room
import com.example.samplemvvmnotepad.data.db.AppDatabase
import com.example.samplemvvmnotepad.data.db.NotesDao

/**
 *  this is a singleton class to get our dependencies
 *
 */
@Deprecated("Replaced with DbModule")
object AppInjector {

    /**
     *  the dao object we need it to save and get notes from db
     */
    private var dao: NotesDao? = null

    private const val DB_NAME = "notes_db"


    fun getDao(context: Context): NotesDao {

        if (dao == null) {
            // create db and get dao
            dao = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).build().getNotesDao()

        }

        return  dao!!

    }


}