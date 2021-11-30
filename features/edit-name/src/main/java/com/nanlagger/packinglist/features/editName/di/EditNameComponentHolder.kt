package com.nanlagger.packinglist.features.editName.di

import com.nanlagger.packinglist.core.di.ComponentHolder

object EditNameComponentHolder : ComponentHolder<EditNameComponent, EditNameDeps>() {
    override fun createComponent(key: String, deps: EditNameDeps): EditNameComponent {
        return DaggerEditNameComponent.builder()
            .deps(deps)
            .build()
    }
}