package com.nanlagger.packinglist.di

import android.app.Application
import com.nanlagger.packinglist.core.di.DependencyProvider
import com.nanlagger.packinglist.features.editName.di.EditNameComponentHolder
import com.nanlagger.packinglist.features.editName.di.EditNameDeps
import com.nanlagger.packinglist.features.main.di.MainComponentHolder
import com.nanlagger.packinglist.features.main.di.MainDeps
import com.nanlagger.packinglist.features.roster.details.di.RosterComponentHolder
import com.nanlagger.packinglist.features.roster.details.di.RosterDeps
import com.nanlagger.packinglist.features.roster.flow.di.RosterFlowComponentHolder
import com.nanlagger.packinglist.features.roster.flow.di.RosterFlowDeps
import com.nanlagger.packinglist.features.roster.list.di.RosterListComponentHolder
import com.nanlagger.packinglist.features.roster.list.di.RosterListDeps

object ComponentManager {

    init {
        MainComponentHolder.dependencyProvider = object : DependencyProvider<MainDeps> {
            override fun get(key: String): MainDeps {
                return appComponent ?: error("AppComponent not initialized")
            }
        }
        RosterFlowComponentHolder.dependencyProvider = object : DependencyProvider<RosterFlowDeps> {
            override fun get(key: String): RosterFlowDeps {
                return MainComponentHolder.getComponent(key)
            }
        }
        RosterListComponentHolder.dependencyProvider = object : DependencyProvider<RosterListDeps> {
            override fun get(key: String): RosterListDeps {
                return RosterFlowComponentHolder.getComponent(key)
            }
        }
        RosterComponentHolder.dependencyProvider = object : DependencyProvider<RosterDeps> {
            override fun get(key: String): RosterDeps {
                return RosterFlowComponentHolder.getComponent(key)
            }
        }
        EditNameComponentHolder.dependencyProvider = object : DependencyProvider<EditNameDeps> {
            override fun get(key: String): EditNameDeps {
                return RosterListComponentHolder.getComponentOrNull(key) ?: RosterComponentHolder.getComponent(key)
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