package com.nanlagger.packinglist.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.nanlagger.packinglist.data.database.entities.RosterEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RosterEntity(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ID) var id: Long = 0,
        @ColumnInfo(name = NAME) var name: String = "",
        @ColumnInfo(name = PRIORITY) var priority: Int = 0
) {

    companion object {

        const val TABLE_NAME = "rosters"

        const val ID = "id"
        const val NAME = "name"
        const val PRIORITY = "priority"
    }
}