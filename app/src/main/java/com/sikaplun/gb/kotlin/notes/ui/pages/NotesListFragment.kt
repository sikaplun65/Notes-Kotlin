package com.sikaplun.gb.kotlin.notes.ui.pages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sikaplun.gb.kotlin.notes.databinding.FragmentNotesListBinding
import com.sikaplun.gb.kotlin.notes.domain.repo.NotesListImpl
import com.sikaplun.gb.kotlin.notes.domain.repository.Noteslist
import com.sikaplun.gb.kotlin.notes.domain.model.NoteEntity
import com.sikaplun.gb.kotlin.notes.ui.adapter.NoteAdapter

class NotesListFragment : Fragment(), NoteAdapter.InteractionListener {

    private var controller: Controller? = null
    private var id: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter
    private var notesList: Noteslist = NotesListImpl.getNotesList()

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = if (context is Controller) context else {
            throw IllegalStateException("must implement NotesListFragment.Controller")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NoteAdapter(notesList.getNotes(), this)
        initializationAddNewNoteButton()
        initializationRecyclerView(view)
    }

    private fun initializationAddNewNoteButton() {
        binding.newNoteButton.setOnClickListener { controller?.startNotesCreateFragment() }
    }

    private fun initializationRecyclerView(view: View) {
        recyclerView = binding.recyclerView
        recyclerView.setLayoutManager(LinearLayoutManager(view.context))
        recyclerView.setAdapter(adapter)
        adapter.setData(notesList.getNotes())

        adapter.setOnItemClickListener(object : NoteAdapter.InteractionListener {
            override fun OnItemShortClick(item: NoteEntity?) {
                onItemClick(item)
            }
        })
    }

    private fun onItemClick(note: NoteEntity?) {
        id = note?.id
        controller?.startNotesEditFragment(id)
    }

    override fun onDestroy() {
        controller = null
        _binding = null
        super.onDestroy()
    }

    interface Controller {
        fun startNotesCreateFragment()
        fun startNotesEditFragment(id: String?)
    }

    override fun OnItemShortClick(item: NoteEntity?) {}
}