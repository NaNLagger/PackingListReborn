package com.nanlagger.note.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nanlagger.note.domain.Note
import com.nanlagger.note.domain.interactors.NoteInteractor
import com.nanlagger.note.navigation.NoteScreenData
import com.nanlagger.note.navigation.Screens
import com.nanlagger.utils.addTo
import com.nanlagger.utils.async
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class NoteListViewModel(
        private val router: Router,
        private val noteInteractor: NoteInteractor,
        private val scopeName: String
) : ViewModel() {

    val noteList: LiveData<List<Note>>
        get() = noteListLiveData

    val editMode: LiveData<Boolean>
        get() = editModeLiveData

    private val noteListLiveData: MutableLiveData<List<Note>> = MutableLiveData()
    private val editModeLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var noteItems: MutableList<Note> = mutableListOf()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var firstAttach = true

    fun init() {
        if (firstAttach) loadNotes()
        firstAttach = false
    }

    fun newNote(type: String) {
        noteInteractor.createNote(type)
                .async()
                .subscribe({
                    router.navigateTo(Screens.NOTE_SCREEN, NoteScreenData(type, scopeName, it))
                }, { error -> Timber.e(error) })
                .addTo(compositeDisposable)
    }

    fun clickNote(note: Note) {
        router.navigateTo(Screens.NOTE_SCREEN, NoteScreenData(note.type, scopeName, note.id))
    }

    fun longClickNote(note: Note) {
        editModeLiveData.value = true
    }

    fun changePriority(oldPosition: Int, newPosition: Int) {
        val roster = noteItems[oldPosition]
        noteItems.removeAt(oldPosition)
        noteItems.add(newPosition, roster)
        noteListLiveData.value = noteItems.toList()
    }

    fun saveOrder() {
        val changedNotes = noteItems
                .filterIndexed { index, note ->
                    val nIndex = noteItems.size - index
                    val isChanged = note.priority != nIndex
                    if(isChanged) {
                        note.priority = nIndex
                    }
                    isChanged
                }
        if (changedNotes.isNotEmpty()) {
            noteInteractor.changePriority(changedNotes)
                    .async()
                    .subscribe({}, { error -> Timber.e(error)})
                    .addTo(compositeDisposable)
        }
    }

    fun deleteNote(position: Int) {
        noteInteractor.deleteNote(noteItems[position].id)
                .async()
                .subscribe({}, { error -> Timber.e(error)})
                .addTo(compositeDisposable)
    }

    fun back() {
        val isEdit = editModeLiveData.value ?: false
        if (isEdit) {
            editModeLiveData.value = false
        } else {
            router.exit()
        }
    }

    private fun loadNotes() {
        noteInteractor.getNotes()
                .subscribe({ notes ->
                    noteItems = notes.asSequence().sortedByDescending { it.priority }.toMutableList()
                    noteListLiveData.value = noteItems.toList()
                }, { error -> Timber.e(error)})
                .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val router: Router, private val interactor: NoteInteractor, private val scopeName: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (NoteListViewModel::class.java.isAssignableFrom(modelClass)) {
                NoteListViewModel(router, interactor, scopeName) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}