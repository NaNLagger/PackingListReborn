package com.nanlagger.packinglist.di

import com.nanlagger.packinglist.ui.roster.RosterFragment
import dagger.Component

@Component(modules = [RosterModule::class], dependencies = [RosterDeps::class])
@ScreenScope
interface RosterComponent {

    fun inject(fragment: RosterFragment)

    @Component.Builder
    interface Builder {

        fun deps(deps: RosterDeps): Builder

        fun build(): RosterComponent
    }
}