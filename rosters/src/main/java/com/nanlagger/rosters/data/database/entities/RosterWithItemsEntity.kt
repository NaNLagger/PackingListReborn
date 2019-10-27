package com.nanlagger.rosters.data.database.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.nanlagger.rosters.data.database.entities.RosterEntity
import com.nanlagger.rosters.data.database.entities.RosterItemEntity

data class RosterWithItemsEntity(
        @Embedded var roster: RosterEntity
) {
    @Relation(parentColumn = RosterEntity.ID, entityColumn = RosterItemEntity.ROSTER_ID, entity = RosterItemEntity::class) var items: List<RosterItemEntity> = emptyList()
}