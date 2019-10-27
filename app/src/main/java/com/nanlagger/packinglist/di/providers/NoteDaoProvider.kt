package com.nanlagger.packinglist.di.providers

import com.nanlagger.note.data.NoteDao
import com.nanlagger.packinglist.data.database.AppDatabase
import javax.inject.Inject
import javax.inject.Provider

class NoteDaoProvider @Inject constructor(
        private val database: AppDatabase
) : Provider<NoteDao> {
    override fun get(): NoteDao {
        return database.getNoteDao()
    }
}