package com.nanlagger.packinglist.features.main.di

import com.nanlagger.packinglist.core.di.ComponentHolder
import com.nanlagger.packinglist.features.main.di.MainComponent
import com.nanlagger.packinglist.features.main.di.MainDeps

object MainComponentHolder : ComponentHolder<MainComponent, MainDeps>() {

    override fun createComponent(key: String, deps: MainDeps): MainComponent {
        return DaggerMainComponent.builder()
            .deps(deps)
            .build()
    }
}