package com.nanlagger.packinglist.di

object MainComponentHolder : ComponentHolder<MainComponent, MainDeps>() {

    override fun createComponent(key: String, deps: MainDeps): MainComponent {
        return DaggerMainComponent.builder()
            .deps(deps)
            .build()
    }
}