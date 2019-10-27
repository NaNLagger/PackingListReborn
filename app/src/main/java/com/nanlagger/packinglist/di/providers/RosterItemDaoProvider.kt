package com.nanlagger.packinglist.di.providers

import com.nanlagger.packinglist.data.database.AppDatabase
import com.nanlagger.rosters.data.database.dao.RosterItemDao
import javax.inject.Inject
import javax.inject.Provider

class RosterItemDaoProvider @Inject constructor(
        private val appDatabase: AppDatabase
) : Provider<RosterItemDao> {
    override fun get(): RosterItemDao {
        return appDatabase.getRosterItemDao()
    }
}