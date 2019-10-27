package com.nanlagger.note.domain

interface Note {
    val id: Long
    var priority: Int
    val type: String
}