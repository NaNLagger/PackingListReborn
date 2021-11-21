package com.nanlagger.packinglist.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.nanlagger.packinglist.ui.roster.RosterFragment
import com.nanlagger.packinglist.ui.rosters.RosterListFragment

object Screens {
    const val SHARED_NAME_TOOLBAR = "toolbar"
    const val SHARED_NAME_TITLE = "title"
    const val SHARED_NAME_CONTAINER = "container"

}

object RosterListScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment {
        return RosterListFragment.newInstance()
    }
}

class RosterScreen(val rosterId: Long): FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment {
        return RosterFragment.newInstance(rosterId)
    }
}