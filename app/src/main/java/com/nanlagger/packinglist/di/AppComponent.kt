package com.nanlagger.packinglist.di

import android.app.Application
import com.nanlagger.packinglist.features.main.di.MainDeps
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DatabaseModule::class, NavigationModule::class, RepositoryModule::class])
@AppScope
interface AppComponent : MainDeps {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}