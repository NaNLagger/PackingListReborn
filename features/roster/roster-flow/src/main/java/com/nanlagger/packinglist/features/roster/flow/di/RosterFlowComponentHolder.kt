package com.nanlagger.packinglist.features.roster.flow.di

import com.nanlagger.packinglist.core.di.ComponentHolder

object RosterFlowComponentHolder : ComponentHolder<RosterFlowComponent, RosterFlowDeps>() {
    override fun createComponent(key: String, deps: RosterFlowDeps): RosterFlowComponent {
        return DaggerRosterFlowComponent.builder()
            .deps(deps)
            .build()
    }
}