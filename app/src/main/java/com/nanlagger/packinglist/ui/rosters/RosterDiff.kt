package com.nanlagger.packinglist.ui.rosters

import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.tools.ListDiffCallback

class RosterDiff(oldList: List<Roster>, newList: List<Roster>) : ListDiffCallback<Roster>(oldList, newList) {
    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].id == newList[newPosition].id
    }
}