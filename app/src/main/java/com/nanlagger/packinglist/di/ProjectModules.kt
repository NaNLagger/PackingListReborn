package com.nanlagger.packinglist.di

import android.app.Application
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.core.common.ResourcesProvider
import com.nanlagger.packinglist.data.database.AppDatabase
import com.nanlagger.packinglist.features.roster.data.dao.RosterDao
import com.nanlagger.packinglist.features.roster.data.dao.RosterItemDao
import com.nanlagger.packinglist.features.roster.data.repositories.RosterItemRepositoryImpl
import com.nanlagger.packinglist.features.roster.data.repositories.RosterRepositoryImpl
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterItemRepository
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository
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

@Module
class RepositoryModule {

    @Provides
    fun provideRosterRepository(rosterDao: RosterDao): RosterRepository {
        return RosterRepositoryImpl(rosterDao)
    }

    @Provides
    fun provideRosterItemRepository(rosterItemDao: RosterItemDao): RosterItemRepository {
        return RosterItemRepositoryImpl(rosterItemDao)
    }
}

@Module(includes = [DatabaseModule::class, NavigationModule::class, RepositoryModule::class])
class ApplicationModule {

    @Provides
    fun provideResourcesProvider(application: Application): ResourcesProvider {
        return ResourcesProvider(application.resources)
    }
}