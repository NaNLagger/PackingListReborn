package com.nanlagger.packinglist.core.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router

abstract class BaseViewModel(private val router: Router) : ViewModel() {

    private var firstAttach = true

    @CallSuper
    open fun onAttach() {
        if (firstAttach) {
            onFirstAttach()
        }
        firstAttach = false
    }

    protected open fun onFirstAttach() {}

    fun back() = router.exit()
}