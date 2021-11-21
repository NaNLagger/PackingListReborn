package com.nanlagger.packinglist.navigation

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.ui.roster.RosterFragment
import com.nanlagger.packinglist.ui.rosters.RosterListFragment

class AppNavigator(
    activity: FragmentActivity,
    containerId: Int
) : AppNavigator(activity, containerId) {

    override fun applyCommand(command: Command) {
        hideKeyboard() //force close keyboard on SAMSUNG!!! device
        super.applyCommand(command)
    }

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        if (currentFragment is RosterListFragment && nextFragment is RosterFragment) {
            setupFragmentTransactionForRosterListToRoster(currentFragment, nextFragment, fragmentTransaction)
            return
        }
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.pop_fade_in, R.anim.fade_out)
    }


    private fun setupFragmentTransactionForRosterListToRoster(
        rosterListFragment: RosterListFragment,
        rosterFragment: RosterFragment,
        fragmentTransaction: FragmentTransaction?
    ) {
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