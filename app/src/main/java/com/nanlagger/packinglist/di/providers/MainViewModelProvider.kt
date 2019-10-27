package com.nanlagger.packinglist.di.providers

import android.arch.lifecycle.ViewModelProviders
import com.nanlagger.packinglist.ui.main.MainActivity
import com.nanlagger.packinglist.ui.main.MainViewModel
import javax.inject.Inject
import javax.inject.Provider

class MainViewModelProvider @Inject constructor(
        private val mainActivity: MainActivity,
        private val factory: MainViewModel.Factory
) : Provider<MainViewModel> {
    override fun get(): MainViewModel {
        return ViewModelProviders.of(mainActivity, factory).get(MainViewModel::class.java)
    }

}