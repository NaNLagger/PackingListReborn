package com.nanlagger.packinglist.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nanlagger.note.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel(
        private val router: Router
) : ViewModel() {

    private var firstAttach = true

    fun init() {
        if (firstAttach) {
            router.newRootScreen(Screens.NOTES_LIST_SCREEN)
        }
        firstAttach = false
    }

    fun back() {
        router.exit()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val router: Router) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (MainViewModel::class.java.isAssignableFrom(modelClass)) {
                MainViewModel(router) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }

}