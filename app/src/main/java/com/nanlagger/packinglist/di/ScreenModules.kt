package com.nanlagger.packinglist.di

import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.domain.interactors.RosterItemInteractor
import com.nanlagger.packinglist.ui.main.MainViewModel
import com.nanlagger.packinglist.ui.roster.RosterViewModel
import com.nanlagger.packinglist.ui.rosters.RosterListViewModel
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @MainScope
    fun provideMainViewModelFactory(router: Router): MainViewModel.Factory {
        return MainViewModel.Factory(router)
    }
}

@Module
class RosterListModule {

    @Provides
    @ScreenScope
    fun provideRosterListViewModelFactory(router: Router, interactor: RosterInteractor): RosterListViewModel.Factory {
        return RosterListViewModel.Factory(router, interactor)
    }
}

@Module
class RosterModule {

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