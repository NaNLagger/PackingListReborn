package com.nanlagger.packinglist.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nanlagger.packinglist.navigation.Screens
import ru.terrakok.cicerone.Router

class MainViewModel(
        private val router: Router
) : ViewModel() {

    private var firstAttach = true

    fun init() {
        if (firstAttach) {
            router.newRootScreen(Screens.ROSTERS_LIST_SCREEN)
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