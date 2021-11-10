package com.sikaplun.gb.kotlin.notes.ui.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sikaplun.gb.kotlin.notes.R
import com.sikaplun.gb.kotlin.notes.domain.repo.NotesListImpl
import com.sikaplun.gb.kotlin.notes.domain.repository.Noteslist
import com.sikaplun.gb.kotlin.notes.databinding.FragmentSortNotesBinding

class SortNotesFragment : Fragment() {
    private var notesList: Noteslist = NotesListImpl.getNotesList()
    private var _binding: FragmentSortNotesBinding? = null
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSortNotesBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.sortFromOldToNewNotesButton?.setOnClickListener {
            notesList.sortFromOldToNewNotes()
            requireActivity().onBackPressed()
        }
        binding?.sortFromNewToOldNotesButton?.setOnClickListener {
                notesList.sortFromNewToOldNotes()
                requireActivity().onBackPressed()
            }
        binding?.sort1ByModifiedDateButton?.setOnClickListener {
            notesList.sorByDateModifiedNotes()
            requireActivity().onBackPressed()
        }
    }
}