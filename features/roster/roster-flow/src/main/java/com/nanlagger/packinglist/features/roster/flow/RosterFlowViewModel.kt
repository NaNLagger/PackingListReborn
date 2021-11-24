package com.nanlagger.packinglist.features.roster.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.features.roster.flow.navigation.RosterListScreen

class RosterFlowViewModel(private val router: Router): ViewModel() {

    private var firstAttach = true

    fun init() {
        if (firstAttach) {
            router.newRootScreen(RosterListScreen())
        }
        firstAttach = false
    }

    fun back() {
        router.exit()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        val router: Router
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (RosterFlowViewModel::class.java.isAssignableFrom(modelClass)) {
                RosterFlowViewModel(router) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}