package com.sikaplun.gb.kotlin.notes.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sikaplun.gb.kotlin.notes.domain.model.NoteEntity

class NoteAdapter(
    private var list: List<NoteEntity>,
    private var clickListener: InteractionListener
) : RecyclerView.Adapter<NoteViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<NoteEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent, clickListener)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): NoteEntity {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnItemClickListener(listener: InteractionListener) {
        clickListener = listener
    }

    interface InteractionListener {
        fun OnItemShortClick(item: NoteEntity?)
        fun OnItemLongClick(item: NoteEntity?): Boolean
    }
}