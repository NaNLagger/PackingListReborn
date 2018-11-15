package com.nanlagger.packinglist.data.database.entities

import android.arch.persistence.room.Embedded

data class RosterWithInfoEntity(
        @Embedded var roster: RosterEntity,
        var countItems: Int,
        var checkedItems: Int
)