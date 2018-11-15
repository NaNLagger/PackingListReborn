package com.nanlagger.packinglist

import android.app.Application
import com.nanlagger.packinglist.di.databaseModule
import com.nanlagger.packinglist.di.navigationModule
import com.nanlagger.packinglist.di.repositoryModule
import com.nanlagger.packinglist.di.schedulerModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import timber.log.Timber

class ProjectApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        bind() from instance(this@ProjectApplication as Application)

        import(databaseModule)
        import(repositoryModule)
        import(navigationModule)
        import(schedulerModule)
    }

    override fun onCreate() {
        super. onCreate()
        kodeinTrigger?.trigger()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}