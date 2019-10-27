package com.nanlagger.note.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.nanlagger.note.data.NoteEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class NoteEntity(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ID) var id: Long = 0,
        @ColumnInfo(name = PRIORITY) var priority: Int = 0,
        @ColumnInfo(name = TYPE) var type: String = ""
) {

    companion object {
        const val TABLE_NAME = "notes"

        const val ID = "id"
        const val PRIORITY = "priority"
        const val TYPE = "type"
    }
}