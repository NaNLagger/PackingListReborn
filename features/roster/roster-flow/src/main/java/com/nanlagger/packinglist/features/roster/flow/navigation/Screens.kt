package com.nanlagger.packinglist.features.roster.flow.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.nanlagger.packinglist.features.roster.details.ui.RosterFragment
import com.nanlagger.packinglist.features.roster.flow.RosterFlowFragment
import com.nanlagger.packinglist.features.roster.list.ui.RosterListFragment

class RosterListScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment {
        return RosterListFragment.newInstance()
    }
}

class RosterScreen(val rosterId: Long) : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment {
        return RosterFragment.newInstance(rosterId)
    }
}

class RosterFlowScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment {
        return RosterFlowFragment.newInstance()
    }
}