package com.sikaplun.gb.kotlin.notes.domain.repository

import com.sikaplun.gb.kotlin.notes.domain.model.NoteEntity

interface Noteslist {
    fun getNotes():List<NoteEntity>
    fun getNote(id: String?): NoteEntity?
    fun addNote(note: NoteEntity?)
    fun removeNote(note: NoteEntity?)
    fun sortFromOldToNewNotes()
    fun sortFromNewToOldNotes()
    fun sorByDateModifiedNotes()
}