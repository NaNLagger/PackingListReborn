package com.nanlagger.packinglist.ui.rosters

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.terrakok.cicerone.Router

class RostersListViewModel(
    private val router: Router
) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(val router: Router) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (RostersListViewModel::class.java.isAssignableFrom(modelClass)) {
                RostersListViewModel(router) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }
        }
    }
}