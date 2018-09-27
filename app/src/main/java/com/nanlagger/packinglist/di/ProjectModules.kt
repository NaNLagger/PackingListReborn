package com.nanlagger.packinglist.di

import android.app.Application
import android.arch.persistence.room.Room
import com.nanlagger.packinglist.data.database.AppDatabase
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

const val UI_THREAD = "UI_THREAD"
const val IO_THREAD = "IO_THREAD"

val databaseModule = Kodein.Module("DatabaseModule") {

    bind<AppDatabase>() with singleton { Room.databaseBuilder(instance<Application>(), AppDatabase::class.java, "data.db").build() }

}

val navigationModule = Kodein.Module("NavigationModule") {

    bind<Cicerone<Router>>() with singleton { Cicerone.create() }

    bind<Router>() with singleton { instance<Cicerone<Router>>().router }

    bind<NavigatorHolder>() with singleton {
        instance<Cicerone<Router>>().navigatorHolder
    }
}

val schedulerModule = Kodein.Module("SchedulerModule") {

    bind<Scheduler>(tag = UI_THREAD) with singleton { AndroidSchedulers.mainThread() }

    bind<Scheduler>(tag = IO_THREAD) with singleton { Schedulers.io() }
}