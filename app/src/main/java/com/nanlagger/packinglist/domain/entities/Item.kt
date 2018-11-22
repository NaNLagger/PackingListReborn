package com.nanlagger.packinglist.domain.entities

sealed class Item(open val id: Long, open var name: String)

data class RosterItem(override val id: Long, override var name: String, val rosterId: Long, var checked: Boolean): Item(id, name)

class TemplateItem(id: Long, name: String, val templateId: Long): Item(id, name)