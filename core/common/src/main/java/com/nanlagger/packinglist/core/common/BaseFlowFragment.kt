package com.nanlagger.packinglist.core.common

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import timber.log.Timber

abstract class BaseFlowFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_flow
    open val containerId: Int = R.id.container

    protected abstract val navigationHolder: NavigatorHolder

    protected open val navigator: FlowNavigator by lazy {
        FlowNavigator(this)
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }
}