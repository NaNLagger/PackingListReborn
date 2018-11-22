package com.nanlagger.packinglist.navigation

import android.content.Context
import android.content.Intent
import android.support.transition.ChangeBounds
import android.support.transition.ChangeTransform
import android.support.transition.TransitionSet
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.ui.roster.RosterFragment
import com.nanlagger.packinglist.ui.rosters.RosterListFragment
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

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
        Screens.ROSTERS_LIST_SCREEN -> RosterListFragment.newInstance()
        Screens.ROSTER_SCREEN -> if (data is Long) RosterFragment.newInstance(data) else RosterFragment.newInstance(0L)
        else -> null
    }

    override fun setupFragmentTransactionAnimation(command: Command?, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction?) {
        if (command is Forward && currentFragment is RosterListFragment && nextFragment is RosterFragment) {
            setupFragmentTransactionForRosterListToRoster(currentFragment, nextFragment, fragmentTransaction)
            return
        }
        fragmentTransaction?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.pop_fade_in, R.anim.fade_out)
    }

    private fun setupFragmentTransactionForRosterListToRoster(rosterListFragment: RosterListFragment, rosterFragment: RosterFragment, fragmentTransaction: FragmentTransaction?) {
        val changeBounds = ChangeBounds()
        val changeTransform = ChangeTransform()
        val transitionSet = TransitionSet()
        transitionSet.addTransition(changeTransform)
        transitionSet.addTransition(changeBounds)
        rosterListFragment.sharedElementEnterTransition = transitionSet
        rosterListFragment.sharedElementReturnTransition = transitionSet
        rosterFragment.sharedElementEnterTransition = transitionSet
        rosterFragment.sharedElementReturnTransition = transitionSet

        val rosterId = rosterFragment.rosterId

        val containerSharedView = rosterListFragment.containerSharedView
        val toolbarSharedView = rosterListFragment.toolbarSharedView
        val titleSharedView = rosterListFragment.titleSharedView
        containerSharedView?.let { fragmentTransaction?.addSharedElement(it, Screens.SHARED_NAME_CONTAINER + rosterId) }
        toolbarSharedView?.let { fragmentTransaction?.addSharedElement(it, Screens.SHARED_NAME_TOOLBAR + rosterId) }
        titleSharedView?.let { fragmentTransaction?.addSharedElement(it, Screens.SHARED_NAME_TITLE + rosterId) }
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