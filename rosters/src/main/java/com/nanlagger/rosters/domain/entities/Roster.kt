package com.nanlagger.rosters.domain.entities

import com.nanlagger.note.domain.Note

data class Roster(
        override val id: Long,
        override var priority: Int,
        var name: String,
        var items: List<RosterItem>
) : Note {
    override val type: String = "Roster"
    val checkedCount = items.count { it.checked }
    val totalCount = items.size
    val progress = ((checkedCount.toFloat() / totalCount) * 100).toInt()

    companion object {
        val EMPTY = Roster(0L, 0, "", emptyList())
    }
}