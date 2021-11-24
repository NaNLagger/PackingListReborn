package com.nanlagger.packinglist.features.main.di

import com.nanlagger.packinglist.features.roster.flow.di.RosterFlowDeps
import com.nanlagger.packinglist.features.main.ui.MainActivity
import dagger.Component

@Component(modules = [MainModule::class], dependencies = [MainDeps::class])
@MainScope
interface MainComponent: RosterFlowDeps {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        fun deps(deps: MainDeps): Builder

        fun build(): MainComponent
    }
}