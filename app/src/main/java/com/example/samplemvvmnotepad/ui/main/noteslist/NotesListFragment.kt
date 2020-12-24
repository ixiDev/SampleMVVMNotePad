package com.example.samplemvvmnotepad.ui.main.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvvmnotepad.R
import com.example.samplemvvmnotepad.common.MyActionModeCallback
import com.example.samplemvvmnotepad.common.setVisible
import com.example.samplemvvmnotepad.ui.main.createnote.CreateNoteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListFragment : Fragment() {

    private val viewModel: NotesListViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter
    private lateinit var emptyView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_list_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.recyclerView = view.findViewById(R.id.notes_recycler_view)
        this.emptyView = view.findViewById(R.id.empty_view)

        this.adapter = NotesAdapter(
            onNoteClick = { note ->  // on note click event

                // send note id to createNoteFragment
                val args = Bundle().also {
                    it.putInt(CreateNoteFragment.NOTE_ID_ARG, note.id)
                }
                findNavController().navigate(R.id.createNoteFragment, args)
            },
            onShowActionMode = {
                //  show action mode
                onShowActionMode()
            },
            onNoteSelected = { isNoteSelected, noteId ->
                // save note id to view model
                viewModel.setNoteSelected(isNoteSelected, noteId)
            }
        )

        this.recyclerView.adapter = this.adapter // set adapter to recyclerView
    }


    private fun onShowActionMode() {
        requireActivity().startActionMode(MyActionModeCallback(
            deleteSelectedNotes = {
                viewModel.deleteSelectedNotes()
            },
            onActionModeClosed = {
                adapter.disableActionMode()
            }
        ))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(requireContext())
//        ).get(NotesListViewModel::class.java)

        viewModel.getAllNotes().observe(viewLifecycleOwner, { data ->
            adapter.submitData(lifecycle, data)
        })

        viewModel.getNotesCount().observe(viewLifecycleOwner) { count ->
            showEmptyView(count == 0)
        }

    }


    private fun showEmptyView(isEmpty: Boolean) {
        this.recyclerView.setVisible(!isEmpty)
        this.emptyView.setVisible(isEmpty)

    }

}