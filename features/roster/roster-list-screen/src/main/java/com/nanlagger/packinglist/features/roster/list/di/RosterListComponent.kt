package com.nanlagger.packinglist.features.roster.list.di

import com.nanlagger.packinglist.core.di.ScreenScope
import com.nanlagger.packinglist.features.roster.list.ui.RosterListFragment
import dagger.Component

@Component(modules = [RosterListModule::class], dependencies = [RosterListDeps::class])
@ScreenScope
interface RosterListComponent {

    fun inject(fragment: RosterListFragment)

    @Component.Builder
    interface Builder {

        fun deps(deps: RosterListDeps): Builder

        fun build(): RosterListComponent
    }
}