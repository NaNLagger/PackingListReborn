package com.nanlagger.packinglist.di.providers

import com.nanlagger.packinglist.data.database.AppDatabase
import com.nanlagger.rosters.data.database.dao.RosterDao
import javax.inject.Inject
import javax.inject.Provider

class RosterDaoProvider @Inject constructor(
        private val database: AppDatabase
) : Provider<RosterDao> {
    override fun get(): RosterDao {
        return database.getRosterDao()
    }
}