package com.nanlagger.packinglist.di.providers

import android.app.Application
import android.arch.persistence.room.Room
import com.nanlagger.packinglist.data.database.AppDatabase
import javax.inject.Inject
import javax.inject.Provider

class AppDatabaseProvider @Inject constructor(
    private val application: Application
) : Provider<AppDatabase> {
    override fun get(): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "data.db").build()
    }
}