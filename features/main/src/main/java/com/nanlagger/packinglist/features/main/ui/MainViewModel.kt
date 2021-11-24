package com.nanlagger.packinglist.features.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.features.roster.flow.navigation.RosterFlowScreen

class MainViewModel(
    private val router: Router
) : ViewModel() {

    private var firstAttach = true

    fun init() {
        if (firstAttach) {
            router.newRootScreen(RosterFlowScreen())
        }
        firstAttach = false
    }

    fun back() {
        router.exit()
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