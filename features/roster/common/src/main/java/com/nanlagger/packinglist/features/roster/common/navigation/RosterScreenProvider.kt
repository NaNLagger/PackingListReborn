package com.nanlagger.packinglist.features.roster.common.navigation

import com.github.terrakok.cicerone.Screen

interface RosterScreenProvider {

    fun getRosterListScreen(): Screen

    fun getRosterDetailsScreen(rosterId: Long): Screen
}