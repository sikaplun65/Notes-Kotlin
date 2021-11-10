package com.sikaplun.gb.kotlin.notes.ui.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sikaplun.gb.kotlin.notes.R
import com.sikaplun.gb.kotlin.notes.databinding.ActivityMainBinding
import com.sikaplun.gb.kotlin.notes.ui.pages.NotesEditFragment.Companion.create
import com.sikaplun.gb.kotlin.notes.ui.pages.NotesListFragment
import com.sikaplun.gb.kotlin.notes.ui.pages.SettingsFragment
import com.sikaplun.gb.kotlin.notes.ui.pages.SortNotesFragment


class MainActivity : AppCompatActivity(), NotesListFragment.Controller {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNotesListFragment(savedInstanceState)
        initBottomNavigationMenu()
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

    @SuppressLint("NonConstantResourceId")
    private fun initBottomNavigationMenu() {
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.setting_notes_nav_menu -> {
                    val filesFragment =
                        supportFragmentManager.findFragmentByTag("setting_notes_fragment")
                    if (filesFragment == null) {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                SettingsFragment(),
                                "setting_notes_fragment"
                            )
                            .addToBackStack(null)
                            .commit()
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.sort_notes_nav_menu -> {
                    val settingsFragment =
                        supportFragmentManager.findFragmentByTag("sort_notes_fragment")
                    if (settingsFragment == null) {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                SortNotesFragment(),
                                "sort_notes_fragment"
                            )
                            .addToBackStack(null)
                            .commit()
                    }
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun startNotesEditFragment(id: String?) {
        addFragment(create(id))
    }

    override fun startNotesCreateFragment() {
        addFragment(create())
    }

}