package com.example.samplemvvmnotepad.di

import android.content.Context
import androidx.room.Room
import com.example.samplemvvmnotepad.data.db.AppDatabase
import com.example.samplemvvmnotepad.data.db.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext


/**
 *  db  di module to provide ( create)  database and dao
 *  we use object ( singleton ) it's like a static in java
 */

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    private const val DB_NAME = "notes_db"

    /**
     *  create and provide dao to use it (inject it) in repository
     */
    @Provides
    fun provideDao(@ApplicationContext context: Context): NotesDao {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build().getNotesDao()
    }

}