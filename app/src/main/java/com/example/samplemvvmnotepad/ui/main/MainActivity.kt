package com.example.samplemvvmnotepad.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.samplemvvmnotepad.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
    }


    fun setupUI() {
        findNavController(R.id.nav_host_controller)
            .addOnDestinationChangedListener { controller, destination, arguments ->
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

    fun goToCreateNote(view: View) {

        // navigate to CreateNoteFragment it's very simple with Navigation Component :D
        findNavController(R.id.nav_host_controller).navigate(
            R.id.createNoteFragment
        )

    }
}