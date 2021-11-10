package com.sikaplun.gb.kotlin.notes.ui.pages

import androidx.lifecycle.ViewModel
import com.sikaplun.gb.kotlin.notes.domain.repo.NotesListImpl
import com.sikaplun.gb.kotlin.notes.domain.repository.Noteslist
import com.sikaplun.gb.kotlin.notes.domain.model.NoteEntity

class NotesEditFragmentViewModel() : ViewModel() {

    private var notesList:Noteslist = NotesListImpl.getNotesList()
    private lateinit var id: String

    fun onClickSaveButton(
        titleEditText: String,
        detailEditText: String,
        tempTitle: String,
        tempDetail: String,
        noteId: String
    ) {
        id = noteId
        var isModifiedDate = true

        if (id.isEmpty()) {
            createNote()
            isModifiedDate = false
        }

        if (isModifiedDate) {
            if (!tempTitle.equals(notesList.getNote(id)?.title)
                || !tempDetail.equals(notesList.getNote(id)?.detail)
            ) {
                notesList.getNote(id)?.setModifiedDate()
            }
        }

        if (tempTitle.isNotEmpty()) {
            notesList.getNote(id)?.title = tempTitle
        }

        if (tempDetail.isNotEmpty()) {
            notesList.getNote(id)?.detail = tempDetail
        }

        createTitleIfEmptyTitle(titleEditText, detailEditText)
        removeNoteIfEmpty(titleEditText, detailEditText)

    }

    private fun removeNoteIfEmpty(titleEditText: String, detailEditText: String) {
        if (titleEditText.isEmpty() && detailEditText.isEmpty()) {
            notesList.removeNote(notesList.getNote(id))
        }
    }

    private fun createTitleIfEmptyTitle(titleEditText: String, detailEditText: String) {
        if (titleEditText.isEmpty() && detailEditText.isNotEmpty()) {
            notesList.getNote(id)?.title = if (detailEditText.length < 10)
                detailEditText
            else detailEditText.substring(0, 10)
        }
    }

    private fun createNote() {
        val newNote = NoteEntity()
        notesList.addNote(newNote)
        id = newNote.id.toString()
    }

}