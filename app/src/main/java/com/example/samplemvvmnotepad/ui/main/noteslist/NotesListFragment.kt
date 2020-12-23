package com.example.samplemvvmnotepad.ui.main.noteslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvvmnotepad.R
import com.example.samplemvvmnotepad.common.ViewModelFactory

class NotesListFragment : Fragment() {

    private lateinit var viewModel: NotesListViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_list_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.recyclerView = view.findViewById(R.id.notes_recycler_view)
        this.adapter = NotesAdapter()
        this.recyclerView.adapter = this.adapter // set adapter to recyclerView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireContext())
        ).get(NotesListViewModel::class.java)

        viewModel.getAllNotes().observe(viewLifecycleOwner, { data ->
            adapter.submitData(lifecycle, data)
        })

    }

}