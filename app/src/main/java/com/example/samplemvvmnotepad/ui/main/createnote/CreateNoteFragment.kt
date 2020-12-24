package com.example.samplemvvmnotepad.ui.main.createnote

import android.os.Bundle
import android.view.*
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.azeesoft.lib.colorpicker.ColorPickerDialog
import com.example.samplemvvmnotepad.R
import com.example.samplemvvmnotepad.common.IAztecToolbarClickListenerImpl
import com.example.samplemvvmnotepad.common.ViewModelFactory
import com.example.samplemvvmnotepad.common.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.wordpress.aztec.Aztec
import org.wordpress.aztec.AztecText
import org.wordpress.aztec.toolbar.AztecToolbar

class CreateNoteFragment : Fragment() {

    private lateinit var viewModel: CreateNoteViewModel

    /**
     * get this note id wen click on note in RecyclerView
     * Default is null if we click to fab button
     */
    private var noteId: Int? = null

    private lateinit var noteInputView: AztecText
    private lateinit var formattingToolbar: AztecToolbar
    private lateinit var noteColorView: View


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
        this.formattingToolbar = view.findViewById(R.id.formatting_toolbar)
        this.noteColorView = view.findViewById(R.id.note_color_view)

        // setup text editor
        Aztec.with(
            this.noteInputView,
            this.formattingToolbar,
            IAztecToolbarClickListenerImpl()

        )

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
                showColorDialog()
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

        viewModel.getNoteColor().observe(viewLifecycleOwner) { hexColor ->

            this.noteColorView.setBackgroundColor(hexColor.toColorInt())
        }

        // receive  note id
        noteId = arguments?.getInt(NOTE_ID_ARG)

        if (noteId != null) {
            // here we have suspend function so we need coroutines scope to run it
            // Main Thread
            lifecycleScope.launch(Dispatchers.Main) {
                val note = viewModel.getNoteById(noteId!!)

                viewModel.saveNoteColor(note.color) // change color note in view model

                //noteInputView.setText(note.text)
                noteInputView.fromHtml(note.text) // use from html because  we save note text as html

            }
        } else {
            noteInputView.text.clear() // note id null so create new note
        }


    }

    private fun onSaveNoteClick() {

        // hide keyboard  , i will use extension function , let's see google :D
        noteInputView.hideKeyboard()

        val text = noteInputView.toFormattedHtml() // get text as html not as normal string

        if (text.isEmpty()) {
            return // no text to save it
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.saveNote(noteId, text) // save note

            findNavController().navigateUp() // go back to home fragment

        }

    }

    private fun showColorDialog() {
        val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(requireContext())
        colorPickerDialog.setOnColorPickedListener { color, hexVal ->
            // send hexVal to viewModel
            viewModel.saveNoteColor(hexVal)
        }
        colorPickerDialog.show()
    }


    companion object {
        const val NOTE_ID_ARG = "note_id"

    }
}