package com.nanlagger.packinglist.features.roster.details.di

import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.di.Dependencies
import com.nanlagger.packinglist.features.roster.common.RosterTransitionAnimationHelper
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterItemRepository
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository

interface RosterDeps: Dependencies {
    val router: Router
    val rosterRepository: RosterRepository
    val rosterItemRepository: RosterItemRepository
    val rosterTransitionAnimationHelper: RosterTransitionAnimationHelper
}