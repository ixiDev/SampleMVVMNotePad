package com.example.samplemvvmnotepad.common

import org.wordpress.aztec.ITextFormat
import org.wordpress.aztec.toolbar.IAztecToolbarClickListener

class IAztecToolbarClickListenerImpl : IAztecToolbarClickListener {
    override fun onToolbarCollapseButtonClicked() {
        // do nothing
    }

    override fun onToolbarExpandButtonClicked() {
        // do nothing
    }

    override fun onToolbarFormatButtonClicked(format: ITextFormat, isKeyboardShortcut: Boolean) {
        // do nothing
    }

    override fun onToolbarHeadingButtonClicked() {
        // do nothing
    }

    override fun onToolbarHtmlButtonClicked() {
        // do nothing
    }

    override fun onToolbarListButtonClicked() {
        // do nothing
    }

    override fun onToolbarMediaButtonClicked(): Boolean {
        return false
    }
}