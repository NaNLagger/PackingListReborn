package com.nanlagger.packinglist.features.roster.domain.entities

data class Roster(
        val id: Long,
        var name: String,
        var priority: Int,
        var items: List<RosterItem>
) {
    val checkedCount = items.count { it.checked }
    val totalCount = items.size
    val progress = ((checkedCount.toFloat() / totalCount) * 100).toInt()

    companion object {
        val EMPTY = Roster(0L, "", 0, emptyList())
    }
}