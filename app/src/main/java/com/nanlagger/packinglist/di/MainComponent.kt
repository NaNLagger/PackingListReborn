package com.nanlagger.packinglist.di

import com.nanlagger.packinglist.ui.main.MainActivity
import dagger.Component

@Component(modules = [MainModule::class], dependencies = [MainDeps::class])
@MainScope
interface MainComponent: RosterListDeps, RosterDeps {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        fun deps(deps: MainDeps): Builder

        fun build(): MainComponent
    }
}