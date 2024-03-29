package com.nanlagger.packinglist.features.roster.list.di

import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.di.ScreenScope
import com.nanlagger.packinglist.features.editName.domain.EditNameInteractor
import com.nanlagger.packinglist.features.roster.common.navigation.RosterScreenProvider
import com.nanlagger.packinglist.features.roster.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository
import com.nanlagger.packinglist.features.editName.domain.EditNameInteractorImpl
import com.nanlagger.packinglist.features.roster.list.ui.RosterListViewModel
import dagger.Module
import dagger.Provides

@Module
class RosterListModule {

    @Provides
    fun provideRosterInteractor(rosterRepository: RosterRepository): RosterInteractor {
        return RosterInteractor(rosterRepository)
    }

    @Provides
    @ScreenScope
    fun provideEditNameInteractorImpl(): EditNameInteractorImpl {
        return EditNameInteractorImpl()
    }

    @Provides
    @ScreenScope
    fun provideEditNameInteractor(editNameInteractorImpl: EditNameInteractorImpl): EditNameInteractor {
        return editNameInteractorImpl
    }

    @Provides
    @ScreenScope
    fun provideRosterListViewModelFactory(
        router: Router,
        interactor: RosterInteractor,
        rosterScreenProvider: RosterScreenProvider,
        editNameInteractorImpl: EditNameInteractorImpl
    ): RosterListViewModel.Factory {
        return RosterListViewModel.Factory(router, interactor, rosterScreenProvider, editNameInteractorImpl)
    }
}