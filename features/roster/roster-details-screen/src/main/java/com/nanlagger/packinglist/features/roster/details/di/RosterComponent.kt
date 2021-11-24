package com.nanlagger.packinglist.features.roster.details.di

import com.nanlagger.packinglist.core.di.ScreenScope
import com.nanlagger.packinglist.features.roster.details.ui.RosterFragment
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