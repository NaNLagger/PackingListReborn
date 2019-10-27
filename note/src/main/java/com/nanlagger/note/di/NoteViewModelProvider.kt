package com.nanlagger.note.di

import android.arch.lifecycle.ViewModelProviders
import com.nanlagger.note.ui.NoteListFragment
import com.nanlagger.note.ui.NoteListViewModel
import javax.inject.Inject
import javax.inject.Provider

class NoteViewModelProvider @Inject constructor(
        private val factory: NoteListViewModel.Factory,
        private val fragment: NoteListFragment
) : Provider<NoteListViewModel> {
    override fun get(): NoteListViewModel {
        return ViewModelProviders.of(fragment, factory).get(NoteListViewModel::class.java)
    }
}