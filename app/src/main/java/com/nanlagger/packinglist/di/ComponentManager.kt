package com.nanlagger.packinglist.di

import android.app.Application

object ComponentManager {

    init {
        MainComponentHolder.dependencyProvider = object : DependencyProvider<MainDeps> {
            override fun get(key: String): MainDeps {
                return appComponent ?: error("AppComponent not initialized")
            }
        }
        RosterListComponentHolder.dependencyProvider = object : DependencyProvider<RosterListDeps> {
            override fun get(key: String): RosterListDeps {
                return MainComponentHolder.getComponent(key)
            }
        }
        RosterComponentHolder.dependencyProvider = object : DependencyProvider<RosterDeps> {
            override fun get(key: String): RosterDeps {
                return MainComponentHolder.getComponent(key)
            }
        }
    }

    private var appComponent: AppComponent? = null

    fun createAppComponent(application: Application): AppComponent {
        return DaggerAppComponent.builder()
            .application(application)
            .build()
            .also { appComponent = it }
    }

}