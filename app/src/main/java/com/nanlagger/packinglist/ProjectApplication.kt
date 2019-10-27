package com.nanlagger.packinglist

import android.app.Application
import com.nanlagger.packinglist.di.Scopes
import com.nanlagger.packinglist.di.modules.DatabaseModule
import com.nanlagger.packinglist.di.modules.NavigationModule
import com.nanlagger.packinglist.di.modules.ProjectModule
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration

class ProjectApplication : Application() {

    override fun onCreate() {
        super. onCreate()
        initToothpick()
        initTimber()
        Toothpick.openScopes(Scopes.APP_SCOPE)
                .installModules(
                        ProjectModule(this),
                        DatabaseModule(),
                        NavigationModule()
                )
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }
}