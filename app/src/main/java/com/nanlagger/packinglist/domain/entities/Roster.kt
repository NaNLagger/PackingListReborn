package com.nanlagger.packinglist.domain.entities

data class Roster(
        val id: Long,
        val name: String,
        val items: List<RosterItem>
) {
    val checkedCount = items.count { it.checked }
    val totalCount = items.size
    val progress = ((checkedCount.toFloat() / totalCount) * 100).toInt()
}