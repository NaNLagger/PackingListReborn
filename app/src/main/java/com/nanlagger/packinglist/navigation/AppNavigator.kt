package com.nanlagger.packinglist.navigation

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.nanlagger.note.navigation.NoteScreenData
import com.nanlagger.note.navigation.Screens
import com.nanlagger.packinglist.R
import com.nanlagger.note.ui.NoteListFragment
import com.nanlagger.packinglist.di.Scopes
import com.nanlagger.rosters.ui.RosterFragment
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class AppNavigator(
        private val activity: FragmentActivity,
        fragmentManager: FragmentManager,
        containerId: Int
) : SupportAppNavigator(activity, fragmentManager, containerId) {

    override fun applyCommand(command: Command) {
        hideKeyboard() //force close keyboard on SAMSUNG!!! device
        super.applyCommand(command)
    }

    override fun createActivityIntent(screenKey: String, data: Any?): Intent? = null

    override fun createFragment(screenKey: String, data: Any?): Fragment? = when (screenKey) {
        Screens.NOTES_LIST_SCREEN -> NoteListFragment.newInstance(Scopes.APP_SCOPE)
        Screens.NOTE_SCREEN -> if (data is NoteScreenData) RosterFragment.newInstance(data.noteId, data.scopeName) else RosterFragment.newInstance(0L, Scopes.APP_SCOPE)
        else -> null
    }

    override fun setupFragmentTransactionAnimation(command: Command?, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction?) {
        fragmentTransaction?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.pop_fade_in, R.anim.fade_out)
    }

    private fun hideKeyboard() {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var currentFocus = activity.currentFocus
        if (currentFocus == null)
            currentFocus = activity.findViewById<View>(android.R.id.content).rootView
        if (currentFocus != null)
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}