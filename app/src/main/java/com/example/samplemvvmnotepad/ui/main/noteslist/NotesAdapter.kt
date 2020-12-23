package com.example.samplemvvmnotepad.ui.main.noteslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.parseAsHtml
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvvmnotepad.R
import com.example.samplemvvmnotepad.common.toDate
import com.example.samplemvvmnotepad.data.entities.Note

/**
 * Notes Adapter add it to recyclerView
 *  we use PagingDataAdapter from Paging 3 library so to make list paging if we have long list of notes
 *
 */

class NotesAdapter
    : PagingDataAdapter<Note, NotesAdapter.NoteHolder>(notesDiffItem) {


    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val noteTextView: TextView = itemView.findViewById(R.id.note_text)
        private val noteDateText: TextView = itemView.findViewById(R.id.note_date)

        fun bind(note: Note) {
            noteTextView.text = note.text.parseAsHtml() // later we will use note as html
            noteDateText.text = note.updatedDate.toDate()  // extension function
        }
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        getItem(position)?.let { note ->
            holder.bind(note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteHolder(
            inflater.inflate(R.layout.note_item_layout, parent, false)
        )

    }

    companion object {
        // create DiffItem object for PagingAdapter
        private val notesDiffItem = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }

    }


}