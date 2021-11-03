package com.sikaplun.gb.kotlin.notes.domain.models

import android.app.Application
import com.sikaplun.gb.kotlin.notes.domain.repository.NoteEntity
import com.sikaplun.gb.kotlin.notes.domain.repository.NotesListImpl
import com.sikaplun.gb.kotlin.notes.domain.repository.Noteslist

class AppModel:Application(),Noteslist {
    private lateinit var noteslist: NotesListImpl

    override fun onCreate() {
        super.onCreate()
        noteslist = NotesListImpl.getNotesList()

    }
    override fun getNotes(): List<NoteEntity> = noteslist.getNotes()

    override fun getNote(id: String?): NoteEntity? = noteslist.getNote(id)

    override fun addNote(note: NoteEntity?) = noteslist.addNote(note)

    override fun removeNote(note: NoteEntity?) = noteslist.removeNote(note)

    override fun sortFromOldToNewNotes() = noteslist.sortFromOldToNewNotes()

    override fun sortFromNewToOldNotes() = noteslist.sortFromNewToOldNotes()

    override fun sorByDateModifiedNotes() = noteslist.sorByDateModifiedNotes()
}