package com.nanlagger.note.domain

import com.nanlagger.note.data.NoteEntity
import io.reactivex.Completable
import io.reactivex.Single

interface NoteMapper<T : Note> {

    val type: String

    fun isType(type: String): Boolean = this.type == type

    fun toEmptyDomain(noteEntity: NoteEntity): T

    fun getDomain(id: Long): Single<T>

    fun create(id: Long): Single<Long>

    fun delete(id: Long): Completable
}