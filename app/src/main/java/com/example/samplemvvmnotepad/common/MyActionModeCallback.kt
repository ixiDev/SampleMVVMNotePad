package com.example.samplemvvmnotepad.common

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.example.samplemvvmnotepad.R

class MyActionModeCallback(

    private val deleteSelectedNotes: () -> Unit,
    private val onActionModeClosed: () -> Unit
) : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {

        // create menu action mode
        mode?.menuInflater?.inflate(R.menu.action_mode_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

        if (item?.itemId == R.id.action_delete_notes) {
            // delete selected notes
            deleteSelectedNotes.invoke()
            mode?.finish()

            return true
        }

        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        onActionModeClosed.invoke()
    }
}