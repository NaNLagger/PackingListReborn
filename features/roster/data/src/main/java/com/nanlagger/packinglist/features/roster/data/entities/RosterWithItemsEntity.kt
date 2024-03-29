package com.nanlagger.packinglist.features.roster.data.entities

import androidx.room.Embedded
import androidx.room.Relation


data class RosterWithItemsEntity(
    @Embedded var roster: RosterEntity
) {
    @Relation(
        parentColumn = RosterEntity.ID,
        entityColumn = RosterItemEntity.ROSTER_ID,
        entity = RosterItemEntity::class
    )
    var items: List<RosterItemEntity> = emptyList()
}