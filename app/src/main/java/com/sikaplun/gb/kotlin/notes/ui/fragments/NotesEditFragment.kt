package com.sikaplun.gb.kotlin.notes.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.sikaplun.gb.kotlin.notes.R
import com.sikaplun.gb.kotlin.notes.databinding.FragmentNotesEditBinding
import com.sikaplun.gb.kotlin.notes.domain.models.AppModel
import com.sikaplun.gb.kotlin.notes.domain.repository.NoteEntity

class NotesEditFragment : Fragment() {
    private lateinit var titleEditText: EditText
    private lateinit var detailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var notesList: AppModel
    private var noteId: String? = null
    private lateinit var tempTitle: String
    private lateinit var tempDetail: String

    private var _binding: FragmentNotesEditBinding? = null
    private val binding get() = _binding!!

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
        tempTitle = ""
        tempDetail = ""
        titleEditText = binding.titleEditText
        detailEditText = binding.detailEditText
        saveButton = binding.saveButton
        notesList = requireActivity().applicationContext as AppModel

        val args = this.arguments
        if (args != null && args.containsKey(ID_KEY)) {
            noteId = args.getString(ID_KEY)
            notesList.getNote(noteId)?.let { fillTextTitleAndTextDetail(it) }
        }
        setupListeners()
    }

    private fun fillTextTitleAndTextDetail(note: NoteEntity) {
        titleEditText.setText(note.title)
        detailEditText.setText(note.detail)
        tempTitle = note.title
        tempDetail = note.detail
    }

    private fun setupListeners() {
        setHints()

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
            var isModifiedDate = true

            if (noteId == null) {
                createNote()
                isModifiedDate = false
            }

            if(isModifiedDate) {
                if (!tempTitle.equals(notesList.getNote(noteId)?.title) || !tempDetail.equals(
                        notesList.getNote(noteId)?.detail)) {
                    notesList.getNote(noteId)?.setModifiedDate()
                }
            }

            if (tempTitle.isNotEmpty()) {
                notesList.getNote(noteId)?.title = tempTitle
            }
            if (tempDetail.isNotEmpty()) {
                notesList.getNote(noteId)?.detail = tempDetail
            }

            if (titleEditText.length().equals(0) && !detailEditText.length().equals(0)) {
                notesList.getNote(noteId)?.title =
                    detailEditText.text.toString().substring(0, 10)
            } else if (isNoteBlank) {
                notesList.removeNote(notesList.getNote(noteId))
            }

            requireActivity().onBackPressed()
        }
    }

    private fun createNote() {
        val newNote = NoteEntity()
        notesList.addNote(newNote)
        noteId = newNote.id
    }

    val isNoteBlank: Boolean
        get() = titleEditText.text.isEmpty() && detailEditText.text.isEmpty()

    fun setHints() {
        if (titleEditText.text.isEmpty()) {
            titleEditText.hint = "Заголовок"
        }
        if (detailEditText.text.isEmpty()) {
            detailEditText.hint = "текст заметки"
        }
    }

    companion object {
        private val ID_KEY = "ID_KEY"
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