package com.nanlagger.packinglist.di

object RosterListComponentHolder : ComponentHolder<RosterListComponent, RosterListDeps>() {

    override fun createComponent(key: String, deps: RosterListDeps): RosterListComponent {
        return DaggerRosterListComponent.builder()
            .deps(deps)
            .build()
    }
}