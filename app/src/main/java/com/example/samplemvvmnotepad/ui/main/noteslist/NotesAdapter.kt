package com.example.samplemvvmnotepad.ui.main.noteslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColorInt
import androidx.core.text.parseAsHtml
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.refactor.library.SmoothCheckBox
import com.example.samplemvvmnotepad.R
import com.example.samplemvvmnotepad.common.toDate
import com.example.samplemvvmnotepad.data.entities.Note

/**
 * Notes Adapter add it to recyclerView
 *  we use PagingDataAdapter from Paging 3 library so to make list paging if we have long list of notes
 *
 *  pass a function as parameter
 *  @param onNoteClick : on note click event listener
 */

class NotesAdapter(
    private val onNoteClick: (note: Note) -> Unit,
    private val onShowActionMode: () -> Unit,
    private val onNoteSelected: (isNoteSelected: Boolean, noteId: Int) -> Unit
) : PagingDataAdapter<Note, NotesAdapter.NoteHolder>(notesDiffItem) {


    private var checkMode: Boolean = false


    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val noteTextView: TextView = itemView.findViewById(R.id.note_text)
        private val noteDateText: TextView = itemView.findViewById(R.id.note_date)
        private val noteCardView: CardView = itemView as CardView
        private val checkBox: SmoothCheckBox = itemView.findViewById(R.id.note_check_box)

        fun bind(
            note: Note,
            isCheckMode: Boolean,
            onNoteSelected: (isNoteSelected: Boolean) -> Unit
        ) {

            noteTextView.text = note.text.parseAsHtml() // later we will use note as html
            noteDateText.text = note.updatedDate.toDate()  // extension function

            checkBox.visibility = if (isCheckMode) View.VISIBLE else View.GONE
            checkBox.setOnCheckedChangeListener { checkBox, isChecked ->

                onNoteSelected.invoke(isChecked)
            }

            // if it's not a white color
            if (!note.color.equals("#ffffff", true))
                noteCardView.setCardBackgroundColor(note.color.toColorInt()) // change card view color with note color
        }

        fun performChecked() {
            checkBox.performClick()
        }
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        getItem(position)?.let { note ->

            holder.bind(note, checkMode) { isNoteSelected ->
                //  save selected note id to viewModel
                onNoteSelected.invoke(isNoteSelected, note.id)
            }

            holder.itemView.setOnLongClickListener {
                checkMode = true // enable check mode
                notifyDataSetChanged()
                holder.performChecked()
                onShowActionMode.invoke()
                false // return false
            }

            holder.itemView.setOnClickListener {
                if (checkMode) {
                    holder.performChecked()
                } else
                    this.onNoteClick.invoke(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteHolder(
            inflater.inflate(R.layout.note_item_layout, parent, false)
        )

    }

    fun disableActionMode() {
        this.checkMode = false
        notifyDataSetChanged()
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