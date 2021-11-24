package com.nanlagger.packinglist.features.roster.details.di

import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.di.ScreenScope
import com.nanlagger.packinglist.features.roster.details.ui.RosterViewModel
import com.nanlagger.packinglist.features.roster.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.features.roster.domain.interactors.RosterItemInteractor
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterItemRepository
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository
import dagger.Module
import dagger.Provides

@Module
class RosterModule {

    @Provides
    fun provideRosterInteractor(rosterRepository: RosterRepository): RosterInteractor {
        return RosterInteractor(rosterRepository)
    }

    @Provides
    fun provideRosterItemInteractor(rosterItemRepository: RosterItemRepository): RosterItemInteractor {
        return RosterItemInteractor(rosterItemRepository)
    }

    @Provides
    @ScreenScope
    fun provideRosterViewModelFactory(
        router: Router,
        rosterInteractor: RosterInteractor,
        rosterItemInteractor: RosterItemInteractor
    ): RosterViewModel.Factory {
        return RosterViewModel.Factory(router, rosterInteractor, rosterItemInteractor)
    }
}