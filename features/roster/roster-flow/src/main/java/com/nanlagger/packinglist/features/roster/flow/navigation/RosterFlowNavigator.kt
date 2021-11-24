package com.nanlagger.packinglist.features.roster.flow.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.nanlagger.packinglist.core.common.FlowNavigator
import com.nanlagger.packinglist.features.roster.common.RosterSharedNames
import com.nanlagger.packinglist.features.roster.details.ui.RosterFragment
import com.nanlagger.packinglist.features.roster.flow.R
import com.nanlagger.packinglist.features.roster.flow.RosterFlowFragment
import com.nanlagger.packinglist.features.roster.list.ui.RosterListFragment

class RosterFlowNavigator(fragment: RosterFlowFragment) : FlowNavigator(fragment) {

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
//        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.pop_fade_in, R.anim.fade_out)
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
        containerSharedView?.let { fragmentTransaction?.addSharedElement(it, RosterSharedNames.SHARED_NAME_CONTAINER + rosterId) }
        toolbarSharedView?.let { fragmentTransaction?.addSharedElement(it, RosterSharedNames.SHARED_NAME_TOOLBAR + rosterId) }
        titleSharedView?.let { fragmentTransaction?.addSharedElement(it, RosterSharedNames.SHARED_NAME_TITLE + rosterId) }
    }
}