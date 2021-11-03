package com.sikaplun.gb.kotlin.notes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sikaplun.gb.kotlin.notes.R
import com.sikaplun.gb.kotlin.notes.databinding.ActivityMainBinding
import com.sikaplun.gb.kotlin.notes.ui.fragments.NotesEditFragment.Companion.create
import com.sikaplun.gb.kotlin.notes.ui.fragments.NotesListFragment


class MainActivity : AppCompatActivity(), NotesListFragment.Controller {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNotesListFragment(savedInstanceState)
    }

    private fun initNotesListFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            return
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, NotesListFragment(), "NOTES_LIST_FRAGMENT")
            .commit()
    }

    private fun addFragment(f: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, f)
            .addToBackStack(null)
            .commit()
    }

    override fun startNotesEditFragment(id: String?) {
        addFragment(create(id))
    }

    override fun startNotesCreateFragment() {
        addFragment(create())
    }
}