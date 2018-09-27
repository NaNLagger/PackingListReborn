package com.nanlagger.packinglist.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.nanlagger.packinglist.data.database.entities.RosterEntity

@Database(entities = [RosterEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

}