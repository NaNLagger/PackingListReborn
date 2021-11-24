package com.nanlagger.packinglist.features.roster.data.entities

import androidx.room.Embedded


data class RosterWithInfoEntity(
    @Embedded var roster: RosterEntity,
    var countItems: Int,
    var checkedItems: Int
)