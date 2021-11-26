package com.nanlagger.packinglist.features.roster.flow.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.nanlagger.packinglist.core.common.FlowNavigator
import com.nanlagger.packinglist.features.roster.common.RosterSharedNames
import com.nanlagger.packinglist.features.roster.common.RosterTransitionAnimationHelper
import com.nanlagger.packinglist.features.roster.details.ui.RosterFragment
import com.nanlagger.packinglist.features.roster.flow.R
import com.nanlagger.packinglist.features.roster.flow.RosterFlowFragment
import com.nanlagger.packinglist.features.roster.list.ui.RosterListFragment

class RosterFlowNavigator(
    fragment: RosterFlowFragment,
    private val rosterTransitionAnimationHelper: RosterTransitionAnimationHelper
) : FlowNavigator(fragment) {

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        if (screen is RosterScreen && currentFragment is RosterListFragment && nextFragment is RosterFragment) {
            setupFragmentTransactionForRosterListToRoster(
                screen.rosterId,
                currentFragment,
                nextFragment,
                fragmentTransaction
            )
            return
        }
    }


    private fun setupFragmentTransactionForRosterListToRoster(
        rosterId: Long,
        rosterListFragment: RosterListFragment,
        rosterFragment: RosterFragment,
        fragmentTransaction: FragmentTransaction?
    ) {
        if (fragmentTransaction == null) return

        val transitionSet = rosterTransitionAnimationHelper.setupTransition(rosterId, fragmentTransaction)

        rosterListFragment.sharedElementEnterTransition = transitionSet
        rosterListFragment.sharedElementReturnTransition = transitionSet

        rosterFragment.sharedElementEnterTransition = transitionSet
        rosterFragment.sharedElementReturnTransition = transitionSet
    }
}