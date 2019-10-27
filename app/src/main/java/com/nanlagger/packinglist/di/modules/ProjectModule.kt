package com.nanlagger.packinglist.di.modules

import android.app.Application
import com.nanlagger.packinglist.di.IoScheduler
import com.nanlagger.packinglist.di.UiScheduler
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import toothpick.config.Module

class ProjectModule(application: Application): Module() {
    init {
        bind(Application::class.java).toInstance(application)
        bind(Scheduler::class.java).withName(IoScheduler::class.java).toInstance(Schedulers.io())
        bind(Scheduler::class.java).withName(UiScheduler::class.java).toInstance(AndroidSchedulers.mainThread())
    }
}