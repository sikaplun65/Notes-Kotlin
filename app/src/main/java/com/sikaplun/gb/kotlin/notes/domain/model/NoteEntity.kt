package com.sikaplun.gb.kotlin.notes.domain.model

import java.util.*

class NoteEntity {
    val createDate: Calendar
    var modifiedDate: Calendar? = null
        private set
    var id: String? = null
        private set
    var title: String
    var detail: String

    constructor() {
        title = ""
        detail = ""
        createDate = Calendar.getInstance()
        generateId()
    }

    // для тестов
    constructor(title: String, detail: String) {
        this.title = title
        this.detail = detail
        createDate = Calendar.getInstance()
        generateId()
    }

    private fun generateId() {
        id = Calendar.getInstance().time.time.toString()
    }

    fun setModifiedDate() {
        modifiedDate = Calendar.getInstance()
    }
}