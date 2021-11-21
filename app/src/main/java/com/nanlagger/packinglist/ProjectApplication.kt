package com.nanlagger.packinglist

import android.app.Application
import com.nanlagger.packinglist.di.ComponentManager
import timber.log.Timber

class ProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentManager.createAppComponent(this)
        Timber.plant(Timber.DebugTree())
    }
}