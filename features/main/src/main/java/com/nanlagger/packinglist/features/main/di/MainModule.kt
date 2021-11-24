package com.nanlagger.packinglist.features.main.di

import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.features.main.di.MainScope
import com.nanlagger.packinglist.features.main.ui.MainViewModel
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