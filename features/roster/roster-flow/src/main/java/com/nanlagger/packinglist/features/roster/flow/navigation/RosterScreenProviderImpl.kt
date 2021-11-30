package com.nanlagger.packinglist.features.roster.flow.navigation

import com.github.terrakok.cicerone.Screen
import com.nanlagger.packinglist.features.roster.common.navigation.RosterScreenProvider

class RosterScreenProviderImpl: RosterScreenProvider {
    override fun getRosterListScreen(): Screen {
        return RosterListScreen()
    }

    override fun getRosterDetailsScreen(rosterId: Long): Screen {
        return RosterScreen(rosterId)
    }

}