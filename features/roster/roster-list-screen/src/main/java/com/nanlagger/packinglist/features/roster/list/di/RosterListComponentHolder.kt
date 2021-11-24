package com.nanlagger.packinglist.features.roster.list.di

import com.nanlagger.packinglist.core.di.ComponentHolder
import com.nanlagger.packinglist.features.roster.list.di.RosterListComponent
import com.nanlagger.packinglist.features.roster.list.di.RosterListDeps

object RosterListComponentHolder : ComponentHolder<RosterListComponent, RosterListDeps>() {

    override fun createComponent(key: String, deps: RosterListDeps): RosterListComponent {
        return DaggerRosterListComponent.builder()
            .deps(deps)
            .build()
    }
}