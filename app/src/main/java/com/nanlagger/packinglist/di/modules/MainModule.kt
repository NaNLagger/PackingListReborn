package com.nanlagger.packinglist.di.modules

import com.nanlagger.packinglist.di.providers.MainViewModelProvider
import com.nanlagger.packinglist.ui.main.MainActivity
import com.nanlagger.packinglist.ui.main.MainViewModel
import toothpick.config.Module

class MainModule(activity: MainActivity) : Module(){
    init {
        bind(MainActivity::class.java).toInstance(activity)
        bind(MainViewModel.Factory::class.java).singletonInScope()
        bind(MainViewModel::class.java).toProvider(MainViewModelProvider::class.java)
    }
}