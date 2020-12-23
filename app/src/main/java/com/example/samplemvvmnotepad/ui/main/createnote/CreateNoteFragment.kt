package com.example.samplemvvmnotepad.ui.main.createnote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.samplemvvmnotepad.R
import com.example.samplemvvmnotepad.common.ViewModelFactory
import com.example.samplemvvmnotepad.common.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNoteFragment : Fragment() {

    private lateinit var viewModel: CreateNoteViewModel

    /**
     * get this note id wen click on note in RecyclerView
     * Default is null if we click to fab button
     */
    private var noteId: Int? = null

    private lateinit var noteInputView: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true) // enable menu for fragment

        return inflater.inflate(R.layout.create_note_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.noteInputView = view.findViewById(R.id.note_input_view)

    }


    /**
     * Override this fun to add menu xml file to the fragment
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_save_note -> {

                onSaveNoteClick()
            }
            R.id.action_pick_note_color -> {
                // TODO: 12/23/20  show ColorDialog to chose note color
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireContext())
        ).get(CreateNoteViewModel::class.java)

        noteId = arguments?.getInt(NOTE_ID_ARG)

        if (noteId != null) {
            // here we have suspend function so we need coroutines scope to run it
            // Main Thread
            lifecycleScope.launch(Dispatchers.Main) {
                val note = viewModel.getNoteById(noteId!!)
                noteInputView.setText(note.text)

            }
        } else {
            noteInputView.text.clear() // note id null so create new note
        }


    }

    private fun onSaveNoteClick() {

        // hide keyboard  , i will use extension function , let's see google :D
        noteInputView.hideKeyboard()

        val text = noteInputView.text.toString()

        if (text.isEmpty()) {
            return // no text to save it
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.saveNote(noteId, text) // save note

            findNavController().navigateUp() // go back to home fragment

        }

    }


    companion object {
        const val NOTE_ID_ARG = "note_id";

    }
}