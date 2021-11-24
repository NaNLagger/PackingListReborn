package com.nanlagger.packinglist.features.roster.details.di

import com.nanlagger.packinglist.core.di.ComponentHolder
import com.nanlagger.packinglist.features.roster.details.di.RosterComponent
import com.nanlagger.packinglist.features.roster.details.di.RosterDeps

object RosterComponentHolder : ComponentHolder<RosterComponent, RosterDeps>() {

    override fun createComponent(key: String, deps: RosterDeps): RosterComponent {
        return DaggerRosterComponent.builder()
            .deps(deps)
            .build()
    }
}