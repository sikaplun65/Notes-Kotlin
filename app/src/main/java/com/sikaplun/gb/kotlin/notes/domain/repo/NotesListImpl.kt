package com.sikaplun.gb.kotlin.notes.domain.repo

import com.sikaplun.gb.kotlin.notes.domain.model.NoteEntity
import com.sikaplun.gb.kotlin.notes.domain.repository.Noteslist

object NotesListImpl: Noteslist {

    private val notesList: MutableList<NoteEntity> = mutableListOf()

    init { filListOfNotesWithTestValues() }

    fun getNotesList(): NotesListImpl {
        return NotesListImpl
    }

    override fun getNotes(): List<NoteEntity> = notesList

    override fun getNote(id: String?): NoteEntity? = notesList.find { it.id.equals(id) }

    override fun addNote(note: NoteEntity?) {
        if (note != null) {
            notesList.add(note)
        }
    }

    override fun removeNote(note: NoteEntity?) {
        notesList.remove(note)
    }

    override fun sortFromOldToNewNotes() = notesList.sortBy { it.createDate }

    override fun sortFromNewToOldNotes() = notesList.sortByDescending { it.createDate }

    override fun sorByDateModifiedNotes() = notesList.sortWith(compareBy(nullsLast(reverseOrder()),{it.modifiedDate}))

    private fun filListOfNotesWithTestValues() {
        val numberOfNotes = 6
        for (i in 0 until numberOfNotes) {
            try {
                // задержка времени при создании для проверки сортировок
                Thread.sleep(10)
                notesList.add(
                    NoteEntity(
                        "Заметка " + (i + 1), "Сайт рыбатекст поможет дизайнеру, верстальщику," +
                                " вебмастеру сгенерировать несколько абзацев более менее осмысленного текста"
                    )
                )
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

}