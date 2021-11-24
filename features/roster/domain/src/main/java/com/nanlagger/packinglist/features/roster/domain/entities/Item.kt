package com.nanlagger.packinglist.features.roster.domain.entities

sealed class Item(open val id: Long, open var name: String)

data class RosterItem(override val id: Long, override var name: String, val rosterId: Long, var checked: Boolean) :
    Item(id, name)