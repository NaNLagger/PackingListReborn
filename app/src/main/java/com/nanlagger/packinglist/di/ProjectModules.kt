package com.nanlagger.packinglist.di

import android.app.Application
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.data.database.AppDatabase
import com.nanlagger.packinglist.data.database.dao.RosterDao
import com.nanlagger.packinglist.data.database.dao.RosterItemDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @AppScope
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "data.db"
        ).build()
    }

    @Provides
    @AppScope
    fun provideRosterDao(appDatabase: AppDatabase): RosterDao {
        return appDatabase.getRosterDao()
    }

    @Provides
    @AppScope
    fun provideRosterItemDao(appDatabase: AppDatabase): RosterItemDao {
        return appDatabase.getRosterItemDao()
    }
}

@Module
class NavigationModule {

    @Provides
    @AppScope
    fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Provides
    @AppScope
    fun provideRouter(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }

    @Provides
    @AppScope
    fun provideNavigationHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}