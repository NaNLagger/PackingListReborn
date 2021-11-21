package com.nanlagger.packinglist.di

object RosterComponentHolder : ComponentHolder<RosterComponent, RosterDeps>() {

    override fun createComponent(key: String, deps: RosterDeps): RosterComponent {
        return DaggerRosterComponent.builder()
            .deps(deps)
            .build()
    }
}