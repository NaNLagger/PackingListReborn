package com.nanlagger.packinglist.features.roster.flow.di

import com.nanlagger.packinglist.core.common.ResourcesProvider
import com.nanlagger.packinglist.core.di.Dependencies
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterItemRepository
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository

interface RosterFlowDeps : Dependencies {
    val rosterRepository: RosterRepository
    val rosterItemRepository: RosterItemRepository
    val resourcesProvider: ResourcesProvider
}