package com.nanlagger.packinglist.features.editName.di

import com.nanlagger.packinglist.core.di.ScreenScope
import com.nanlagger.packinglist.features.editName.domain.EditNameInteractor
import com.nanlagger.packinglist.features.editName.ui.EditNameViewModel
import dagger.Module
import dagger.Provides

@Module
class EditNameModule {

    @Provides
    @ScreenScope
    fun provideEditNameViewModelFactory(
        editNameInteractor: EditNameInteractor
    ): EditNameViewModel.Factory {
        return EditNameViewModel.Factory(editNameInteractor)
    }
}