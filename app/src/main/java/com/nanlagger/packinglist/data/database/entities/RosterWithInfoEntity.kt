package com.nanlagger.packinglist.data.database.entities

import androidx.room.Embedded


data class RosterWithInfoEntity(
    @Embedded var roster: RosterEntity,
    var countItems: Int,
    var checkedItems: Int
)