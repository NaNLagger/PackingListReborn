package com.nanlagger.packinglist.features.roster.list.ui.adapter

import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.utils.adapter.ListDiffCallback

class RosterDiff(oldList: List<Roster>, newList: List<Roster>) : ListDiffCallback<Roster>(oldList, newList) {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
                && oldItem.name == newItem.name
                && oldItem.priority == newItem.priority
                && oldItem.checkedCount == newItem.checkedCount
                && oldItem.totalCount == newItem.totalCount
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].id == newList[newPosition].id
    }
}