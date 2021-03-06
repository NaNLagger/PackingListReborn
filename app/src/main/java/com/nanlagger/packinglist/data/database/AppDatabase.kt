package com.nanlagger.packinglist.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.nanlagger.packinglist.data.database.dao.RosterDao
import com.nanlagger.packinglist.data.database.dao.RosterItemDao
import com.nanlagger.packinglist.data.database.entities.RosterEntity
import com.nanlagger.packinglist.data.database.entities.RosterItemEntity

@Database(entities = [RosterEntity::class, RosterItemEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRosterDao(): RosterDao
    abstract fun getRosterItemDao(): RosterItemDao
}