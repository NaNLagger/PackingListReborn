package com.nanlagger.packinglist.features.main.di

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.common.ResourcesProvider
import com.nanlagger.packinglist.core.di.Dependencies
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterItemRepository
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository

interface MainDeps: Dependencies {
    val navigationHolder: NavigatorHolder
    val router: Router
    val rosterRepository: RosterRepository
    val rosterItemRepository: RosterItemRepository
    val resourcesProvider: ResourcesProvider
}