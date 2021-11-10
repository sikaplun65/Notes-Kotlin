package com.sikaplun.gb.kotlin.notes.ui.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sikaplun.gb.kotlin.notes.databinding.FragmentNotesEditBinding
import com.sikaplun.gb.kotlin.notes.domain.repo.NotesListImpl
import com.sikaplun.gb.kotlin.notes.domain.repository.Noteslist
import com.sikaplun.gb.kotlin.notes.domain.model.NoteEntity

class NotesEditFragment : Fragment() {
    private lateinit var titleEditText: EditText
    private lateinit var detailEditText: EditText
    private lateinit var saveButton: Button
    private var notesList:Noteslist = NotesListImpl.getNotesList()
    private var noteId: String = ""
    private var tempTitle: String = ""
    private var tempDetail: String = ""

    private var _binding: FragmentNotesEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesEditFragmentViewModel: NotesEditFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesEditBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleEditText = binding.titleEditText
        detailEditText = binding.detailEditText
        saveButton = binding.saveButton

        val args = this.arguments
        if (args != null && args.containsKey(ID_KEY)) {
            noteId = args.getString(ID_KEY).toString()
            notesList.getNote(noteId)?.let { fillTextTitleAndTextDetail(it) }
        }

        notesEditFragmentViewModel =
            ViewModelProvider(this).get(
                NotesEditFragmentViewModel::class.java
            )

        setupListeners()
    }

    private fun fillTextTitleAndTextDetail(note: NoteEntity) {
        titleEditText.setText(note.title)
        detailEditText.setText(note.detail)
        tempTitle = note.title
        tempDetail = note.detail
    }

    private fun setupListeners() {

        titleEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tempTitle = titleEditText.text.toString()
            }
        })
        detailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tempDetail = detailEditText.text.toString()
            }
        })

        saveButton.setOnClickListener { v: View? ->
            notesEditFragmentViewModel.onClickSaveButton(
                titleEditText.text.toString(),
                detailEditText.text.toString(),
                tempTitle,
                tempDetail,
                noteId
            )
            requireActivity().onBackPressed()
        }
    }

    companion object {
        val ID_KEY = "ID_KEY"
        fun create(id: String?): NotesEditFragment {
            val fragment = NotesEditFragment()
            val bundle = Bundle().apply { putString(ID_KEY, id) }
            bundle.also { fragment.arguments = it }
            return fragment
        }

        fun create(): NotesEditFragment {
            return NotesEditFragment()
        }
    }
}

