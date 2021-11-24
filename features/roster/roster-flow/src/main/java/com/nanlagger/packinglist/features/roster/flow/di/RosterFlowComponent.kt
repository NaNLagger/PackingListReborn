package com.nanlagger.packinglist.features.roster.flow.di

import com.nanlagger.packinglist.core.di.FlowScope
import com.nanlagger.packinglist.features.roster.details.di.RosterDeps
import com.nanlagger.packinglist.features.roster.flow.RosterFlowFragment
import com.nanlagger.packinglist.features.roster.list.di.RosterListDeps
import dagger.Component

@Component(modules = [RosterFlowModule::class], dependencies = [RosterFlowDeps::class])
@FlowScope
interface RosterFlowComponent: RosterListDeps, RosterDeps {
    fun inject(fragment: RosterFlowFragment)

    @Component.Builder
    interface Builder {

        fun deps(deps: RosterFlowDeps): Builder

        fun build(): RosterFlowComponent
    }
}