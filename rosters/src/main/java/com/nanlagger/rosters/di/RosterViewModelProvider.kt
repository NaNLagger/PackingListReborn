package com.nanlagger.rosters.di

import android.arch.lifecycle.ViewModelProviders
import com.nanlagger.rosters.ui.RosterFragment
import com.nanlagger.rosters.ui.RosterViewModel
import javax.inject.Inject
import javax.inject.Provider

class RosterViewModelProvider @Inject constructor(
        private val factory: RosterViewModel.Factory,
        private val fragment: RosterFragment
) : Provider<RosterViewModel> {
    override fun get(): RosterViewModel {
        return ViewModelProviders.of(fragment, factory).get(RosterViewModel::class.java)
    }
}