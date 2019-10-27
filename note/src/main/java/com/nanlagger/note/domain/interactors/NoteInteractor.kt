package com.nanlagger.note.domain.interactors

import com.nanlagger.note.domain.Note
import com.nanlagger.note.domain.repositories.NoteRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class NoteInteractor(
        private val noteRepository: NoteRepository
) {

    fun getNotes(): Flowable<List<Note>> = noteRepository.getNotes()

    fun createNote(type: String): Single<Long> = noteRepository.createNote(type)

    fun changePriority(notes: List<Note>): Completable = noteRepository.updateNotes(notes)

    fun deleteNote(id: Long): Completable = noteRepository.deleteNote(id)
}