package com.example.samplemvvmnotepad.data.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.samplemvvmnotepad.data.entities.Note

/**
 *  the dao to connect with database
 */
@Dao
interface NotesDao {


    /**
     * this to insert new note to db
     *  IGNORE the note if is duplicate
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notes: Note)

    @Update
    suspend fun update(oldNote: Note)

    @Query("SELECT * FROM notes WHERE id=:noteId")
    suspend fun findNoteById(noteId: Int): Note

    @Query("SELECT * FROM notes ORDER BY updatedDate DESC")
    fun findAllNotes(): PagingSource<Int, Note>

    @Query("DELETE FROM notes WHERE id =:noteId")
    suspend fun deleteNoteById(noteId: Int)

    /**
     * we use this to show empty view when notes count is 0
     */
    @Query("SELECT count(id) FROM notes")
    fun getNotesCount(): LiveData<Int>


}