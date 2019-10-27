package com.nanlagger.note.domain.repositories

import com.nanlagger.note.data.NoteDao
import com.nanlagger.note.data.NoteEntity
import com.nanlagger.note.domain.Note
import com.nanlagger.note.domain.NoteMapperHandler
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

class NoteRepository(
        private val noteDao: NoteDao,
        private val noteMapperHandler: NoteMapperHandler
) {

    fun getNoteTypes() = noteMapperHandler.getNoteTypes()

    fun getNotes(): Flowable<List<Note>> {
        return noteDao.getNotes()
                .flatMap { Single.concat(getEmptyNotes(it), getFullNotes(it)) }
    }

    fun createNote(type: String): Single<Long> {
        return Single.fromCallable { noteDao.insert(NoteEntity()) }
                .flatMap { noteMapperHandler.getMapperByType(type).create(it) }
    }

    fun deleteNote(id: Long): Completable {
        return noteDao.getNoteById(id)
                .first(NoteEntity(id))
                .flatMapCompletable { noteEntity ->
                    Completable.fromCallable { noteDao.delete(NoteEntity(id)) }
                            .mergeWith { noteMapperHandler.getMapperByType(noteEntity.type).delete(id) }
                }
    }

    fun updateNotes(notes: List<Note>): Completable {
        val noteEntities = notes.map { note -> NoteEntity(note.id, note.priority, note.type) }
        return Completable.fromCallable { noteDao.updateAll(noteEntities) }
    }

    private fun getEmptyNotes(notes: List<NoteEntity>): Single<List<Note>> {
        return Observable.fromIterable(notes)
                .map {
                    val mapperByType = noteMapperHandler.getMapperByType(it.type)
                    mapperByType.toEmptyDomain(it)
                }
                .toList()
    }

    private fun getFullNotes(notes: List<NoteEntity>): Single<List<Note>> {
        return Observable.fromIterable(notes)
                .flatMap {
                    val mapperByType = noteMapperHandler.getMapperByType(it.type)
                    mapperByType.getDomain(it.id).toObservable()
                }
                .toList()
    }
}