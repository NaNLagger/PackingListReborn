package com.nanlagger.note.di

import com.nanlagger.note.domain.interactors.NoteInteractor
import com.nanlagger.note.domain.repositories.NoteRepository
import com.nanlagger.note.ui.NoteListFragment
import com.nanlagger.note.ui.NoteListViewModel
import toothpick.config.Module

class NoteModule(fragment: NoteListFragment, scopeName: String) : Module() {
    init {
        bind(NoteListFragment::class.java).toInstance(fragment)
        bind(String::class.java).withName(NoteScopeName::class.java).toInstance(scopeName)
        bind(NoteRepository::class.java).singletonInScope()
        bind(NoteInteractor::class.java).singletonInScope()
        bind(NoteListViewModel.Factory::class.java).singletonInScope()
        bind(NoteListViewModel::class.java).toProvider(NoteViewModelProvider::class.java)
    }
}