package com.nanlagger.packinglist.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.navigation.RosterListScreen
import com.nanlagger.packinglist.navigation.Screens

class MainViewModel(
    private val router: Router
) : ViewModel() {

    private var firstAttach = true

    fun init() {
        if (firstAttach) {
            router.newRootScreen(RosterListScreen)
        }
        firstAttach = false
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val router: Router) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (MainViewModel::class.java.isAssignableFrom(modelClass)) {
                MainViewModel(router) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }

}