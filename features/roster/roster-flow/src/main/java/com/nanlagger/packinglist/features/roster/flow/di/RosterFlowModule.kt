package com.nanlagger.packinglist.features.roster.flow.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.di.FlowScope
import com.nanlagger.packinglist.features.roster.common.RosterTransitionAnimationHelper
import com.nanlagger.packinglist.features.roster.common.navigation.RosterScreenProvider
import com.nanlagger.packinglist.features.roster.flow.RosterFlowViewModel
import com.nanlagger.packinglist.features.roster.flow.navigation.RosterScreenProviderImpl
import dagger.Module
import dagger.Provides

@Module
class RosterFlowModule {

    @Provides
    @FlowScope
    fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Provides
    @FlowScope
    fun provideRouter(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }

    @Provides
    @FlowScope
    fun provideNavigationHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @Provides
    fun provideRosterScreenProvider(): RosterScreenProvider {
        return RosterScreenProviderImpl()
    }

    @Provides
    @FlowScope
    fun provideRosterTransitionAnimationHelper(): RosterTransitionAnimationHelper {
        return RosterTransitionAnimationHelper()
    }

    @Provides
    @FlowScope
    fun provideRosterFlowViewModelFactory(router: Router): RosterFlowViewModel.Factory {
        return RosterFlowViewModel.Factory(router)
    }
}