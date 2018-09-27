package com.nanlagger.packinglist.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.nanlagger.packinglist.data.database.entities.RosterEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RosterEntity(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ID) val id: Long,
        @ColumnInfo(name = NAME) val name: String
) {

    companion object {

        const val TABLE_NAME = "rosters"

        const val ID = "id"
        const val NAME = "name"
    }
}