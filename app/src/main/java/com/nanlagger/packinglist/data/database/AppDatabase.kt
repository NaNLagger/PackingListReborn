package com.nanlagger.packinglist.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.nanlagger.note.data.NoteDao
import com.nanlagger.note.data.NoteEntity
import com.nanlagger.rosters.data.database.dao.RosterDao
import com.nanlagger.rosters.data.database.dao.RosterItemDao
import com.nanlagger.rosters.data.database.entities.RosterEntity
import com.nanlagger.rosters.data.database.entities.RosterItemEntity

@Database(entities = [RosterEntity::class, RosterItemEntity::class, NoteEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRosterDao(): RosterDao
    abstract fun getRosterItemDao(): RosterItemDao
    abstract fun getNoteDao(): NoteDao
}