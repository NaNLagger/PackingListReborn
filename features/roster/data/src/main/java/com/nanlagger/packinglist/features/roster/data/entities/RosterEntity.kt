package com.nanlagger.packinglist.features.roster.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nanlagger.packinglist.features.roster.data.entities.RosterEntity.Companion.TABLE_NAME

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