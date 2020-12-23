package com.example.samplemvvmnotepad.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Note(
    /**
     * note id default 0
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /**
     * note text
     */
    var text: String,
    /**
     *  created date of note we use val so do not change it
     *  and its long so to save datetime in mills
     */
    val createdDate: Long,

    /**
     *  update date , i use var so every time  store i date when change note
     */
    var updatedDate: Long,
    /**
     *  this to save note color ,
     *  default is white color ( hex value )
     */
    var color: String = "#FFFFFF"

)