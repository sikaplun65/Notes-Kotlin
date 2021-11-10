package com.sikaplun.gb.kotlin.notes.ui.pages

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sikaplun.gb.kotlin.notes.R
import com.sikaplun.gb.kotlin.notes.databinding.FragmentNotesListBinding
import com.sikaplun.gb.kotlin.notes.domain.repo.NotesListImpl
import com.sikaplun.gb.kotlin.notes.domain.repository.Noteslist
import com.sikaplun.gb.kotlin.notes.domain.model.NoteEntity
import com.sikaplun.gb.kotlin.notes.ui.adapter.NoteAdapter
import java.lang.IllegalStateException

class NotesListFragment : Fragment(), NoteAdapter.InteractionListener {

    private var controller: Controller? = null
    private var id: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter
    private var notesList: Noteslist = NotesListImpl.getNotesList()
    private lateinit var selectedNotesItem: NoteEntity

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = if (context is Controller) context else {
            throw IllegalStateException("must implement NotesListFragment.Controller")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NoteAdapter(notesList.getNotes(), this)
        initializationAddNewNoteButton()
        initializationRecyclerView(view)
        registerForContextMenu(recyclerView)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.note_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_note -> deleteNoteAlertDialog()
            R.id.menu_edit_note -> onItemClick(selectedNotesItem)
            R.id.menu_share_note -> Toast.makeText(activity, "Пункт меню в разработке", Toast.LENGTH_SHORT).show()
        }
        return super.onContextItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteNoteAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.text_message_alert_dialog_delete_note)
            .setCancelable(false)
            .setPositiveButton(R.string.text_yes) { dialog: DialogInterface?, id: Int ->
                notesList.removeNote(selectedNotesItem)
                adapter.notifyDataSetChanged()
                Snackbar.make(requireView(), R.string.text_note_deleted , Snackbar.LENGTH_LONG).show()
            }
            .setNegativeButton(R.string.text_no, null)
            .show()
    }

    inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
        val snack = Snackbar.make(this, R.string.text_note_deleted, length)
        snack.f()
        snack.show()
    }

    private fun initializationAddNewNoteButton() {
        binding.newNoteButton.setOnClickListener { controller?.startNotesCreateFragment() }
    }

    private fun initializationRecyclerView(view: View) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
        adapter.setData(notesList.getNotes())

        adapter.setOnItemClickListener(object : NoteAdapter.InteractionListener {
            override fun OnItemShortClick(item: NoteEntity?) {
                onItemClick(item)
            }
            override fun OnItemLongClick(item: NoteEntity?): Boolean {
                if (item != null) {
                    selectedNotesItem = item
                }
                return false
            }
        })
    }

    private fun onItemClick(note: NoteEntity?) {
        id = note?.id
        controller?.startNotesEditFragment(id)
    }

    override fun onDestroy() {
        controller = null
        super.onDestroy()
    }

    interface Controller {
        fun startNotesCreateFragment()
        fun startNotesEditFragment(id: String?)
    }

    override fun OnItemShortClick(item: NoteEntity?) {}
    override fun OnItemLongClick(item: NoteEntity?): Boolean =false

}

