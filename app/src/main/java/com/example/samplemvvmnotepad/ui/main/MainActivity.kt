package com.example.samplemvvmnotepad.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.samplemvvmnotepad.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
    }


    fun setupUI() {

        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        val controller = findNavController(R.id.nav_host_controller)

        val appBarConfig = AppBarConfiguration(
            // this is a list of top page ... we will not show toolbar back  button in top page (Fragment)
            setOf(R.id.notesListFragment)
        )
        setupActionBarWithNavController(
            controller,
            appBarConfig
        )

        controller.addOnDestinationChangedListener { _, destination, _ ->
            val fabButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
            when (destination.id) {
                R.id.createNoteFragment -> {
                    fabButton.hide()
                }
                else -> {
                    fabButton.show()
                }
            }
        }

    }

    @Suppress("UNUSED_PARAMETER")
    fun goToCreateNote(view: View) {

        // navigate to CreateNoteFragment it's very simple with Navigation Component :D
        findNavController(R.id.nav_host_controller).navigate(
            R.id.createNoteFragment
        )

    }

    /**
     * override this to navigate back to home page when click on back button
     */
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_controller).navigateUp()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        val switchButton = menu?.findItem(R.id.app_bar_switch_theme)?.actionView as CheckBox

        switchButton.isChecked = isDarkModeEnabled()

        switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            onChangeDarkMode(isChecked)
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun onChangeDarkMode(checked: Boolean) {
        if (checked) {
            // turn on  darkMode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    /**
     * check if dark mode enabled or not
     */
    fun isDarkModeEnabled(): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }


}