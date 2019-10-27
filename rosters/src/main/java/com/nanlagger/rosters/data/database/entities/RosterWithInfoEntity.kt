package com.nanlagger.rosters.data.database.entities

import android.arch.persistence.room.Embedded
import com.nanlagger.rosters.data.database.entities.RosterEntity

data class RosterWithInfoEntity(
        @Embedded var roster: RosterEntity,
        var countItems: Int,
        var checkedItems: Int
)