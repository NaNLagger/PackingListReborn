package com.nanlagger.note.domain

class NoteMapperHandler(private val mappers: List<NoteMapper<Note>>) {

    fun getMapperByType(type: String): NoteMapper<Note> {
        return mappers.find { it.isType(type) } ?: throw IllegalArgumentException("cannot find mapper for type: $type")
    }

    fun getNoteTypes(): List<String> = mappers.map { it.type }
}