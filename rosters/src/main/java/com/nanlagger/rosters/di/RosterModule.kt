package com.nanlagger.rosters.di

import com.nanlagger.rosters.domain.interactors.RosterInteractor
import com.nanlagger.rosters.domain.interactors.RosterItemInteractor
import com.nanlagger.rosters.domain.repositories.RosterItemRepository
import com.nanlagger.rosters.domain.repositories.RosterRepository
import com.nanlagger.rosters.ui.RosterViewModel
import toothpick.config.Module

class RosterModule : Module() {
    init {
        bind(RosterRepository::class.java).singletonInScope()
        bind(RosterInteractor::class.java).singletonInScope()
        bind(RosterItemRepository::class.java).singletonInScope()
        bind(RosterItemInteractor::class.java).singletonInScope()
        bind(RosterViewModel.Factory::class.java).singletonInScope()
        bind(RosterViewModel::class.java).toProvider(RosterViewModelProvider::class.java)
    }
}