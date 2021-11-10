package com.sikaplun.gb.kotlin.notes.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sikaplun.gb.kotlin.notes.R
import com.sikaplun.gb.kotlin.notes.domain.model.NoteEntity
import java.text.SimpleDateFormat
import java.util.*

class NoteViewHolder(parent: ViewGroup, clickListener: NoteAdapter.InteractionListener) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    ) {
    private val createDateTextView = itemView.findViewById<TextView>(R.id.create_date_text_view)
    private val modifiedDateTextView = itemView.findViewById<TextView>(R.id.modified_date_text_view)
    private val titleTextView = itemView.findViewById<TextView>(R.id.title_text_view)
    private val detailTextView = itemView.findViewById<TextView>(R.id.detail_text_view)
    private var note: NoteEntity? = null

    @SuppressLint("SimpleDateFormat")
    fun bind(note: NoteEntity) {
        this.note = note
        modifiedDateTextView.text = getTextModifiedDate(note)
        createDateTextView.text = SimpleDateFormat("dd/MM/yyyy HH:mm").format(note.createDate.time)
        titleTextView.text = note.title
        detailTextView.text = note.detail
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTextModifiedDate(note: NoteEntity): String? {
        return if (note.modifiedDate != null) {
            if (note.modifiedDate!![Calendar.DAY_OF_MONTH] == Calendar.getInstance()[Calendar.DAY_OF_MONTH]
            ) {
                " изменено: сегодня в " + SimpleDateFormat("HH.mm")
                    .format(Calendar.getInstance().time)
            } else " изменено: " + SimpleDateFormat("dd/MM/yyyy  HH.mm")
                .format(note.createDate.time)
        } else null
    }

    init {
        itemView.setOnClickListener { v: View? -> clickListener.OnItemShortClick(note) }
        itemView.setOnLongClickListener { v: View? -> clickListener.OnItemLongClick(note) }
    }

}