package com.nanlagger.packinglist.di

import com.nanlagger.packinglist.ui.rosters.RosterListFragment
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