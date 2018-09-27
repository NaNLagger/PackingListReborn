package com.nanlagger.packinglist.domain.entities

sealed class Item(val id: Long, val name: String)

class RosterItem(id: Long, name: String, val rosterId: Long, val checked: Boolean): Item(id, name)

class TemplateItem(id: Long, name: String, val templateId: Long): Item(id, name)