package com.nanlagger.packinglist.domain.entities

data class Template(
        val id: Long,
        val name: String,
        val items: List<TemplateItem>
)