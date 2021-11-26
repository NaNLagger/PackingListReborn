package com.nanlagger.packinglist.features.roster.list.di

import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.common.ResourcesProvider
import com.nanlagger.packinglist.core.di.Dependencies
import com.nanlagger.packinglist.features.roster.common.RosterTransitionAnimationHelper
import com.nanlagger.packinglist.features.roster.common.navigation.RosterScreenProvider
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository

interface RosterListDeps : Dependencies {
    val rosterRepository: RosterRepository
    val router: Router
    val rosterScreenProvider: RosterScreenProvider
    val rosterTransitionAnimationHelper: RosterTransitionAnimationHelper
    val resourcesProvider: ResourcesProvider
}