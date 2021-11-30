package com.nanlagger.packinglist.features.editName.di

import com.nanlagger.packinglist.core.di.ScreenScope
import com.nanlagger.packinglist.features.editName.ui.EditNameDialog
import dagger.Component

@Component(modules = [EditNameModule::class], dependencies = [EditNameDeps::class])
@ScreenScope
interface EditNameComponent {
    fun inject(fragment: EditNameDialog)

    @Component.Builder
    interface Builder {

        fun deps(deps: EditNameDeps): Builder

        fun build(): EditNameComponent
    }
}