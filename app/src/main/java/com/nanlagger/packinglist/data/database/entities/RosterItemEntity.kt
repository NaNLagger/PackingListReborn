package com.nanlagger.packinglist.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.nanlagger.packinglist.data.database.entities.RosterItemEntity.Companion.TABLE_NAME

@Entity(
        tableName = TABLE_NAME,
        foreignKeys = [ForeignKey(
                entity = RosterEntity::class,
                parentColumns = [RosterEntity.ID],
                childColumns = [RosterItemEntity.ROSTER_ID],
                onDelete = ForeignKey.CASCADE
        )]
)
data class RosterItemEntity(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ID) var id: Long = 0L,
        @ColumnInfo(name = NAME) var name: String = "",
        @ColumnInfo(name = ROSTER_ID) var rosterId: Long = 0L,
        @ColumnInfo(name = IS_CHECKED) var isChecked: Boolean = false
) {

    companion object {
        const val TABLE_NAME = "roster_items"

        const val ID = "id"
        const val NAME = "name"
        const val ROSTER_ID = "roster_id"
        const val IS_CHECKED = "is_checked"
    }
}