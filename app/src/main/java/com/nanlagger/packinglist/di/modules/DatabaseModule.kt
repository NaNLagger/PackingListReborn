package com.nanlagger.packinglist.di.modules

import com.nanlagger.note.data.NoteDao
import com.nanlagger.packinglist.data.database.AppDatabase
import com.nanlagger.packinglist.di.providers.AppDatabaseProvider
import com.nanlagger.packinglist.di.providers.NoteDaoProvider
import com.nanlagger.packinglist.di.providers.RosterDaoProvider
import com.nanlagger.packinglist.di.providers.RosterItemDaoProvider
import com.nanlagger.rosters.data.database.dao.RosterDao
import com.nanlagger.rosters.data.database.dao.RosterItemDao
import toothpick.config.Module

class DatabaseModule : Module() {
    init {
        bind(AppDatabase::class.java).toProvider(AppDatabaseProvider::class.java).providesSingletonInScope()
        bind(RosterDao::class.java).toProvider(RosterDaoProvider::class.java).providesSingletonInScope()
        bind(NoteDao::class.java).toProvider(NoteDaoProvider::class.java).providesSingletonInScope()
        bind(RosterItemDao::class.java).toProvider(RosterItemDaoProvider::class.java).providesSingletonInScope()
    }
}