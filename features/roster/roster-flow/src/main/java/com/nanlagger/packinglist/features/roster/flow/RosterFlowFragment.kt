package com.nanlagger.packinglist.features.roster.flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.NavigatorHolder
import com.nanlagger.packinglist.core.common.BaseFlowFragment
import com.nanlagger.packinglist.core.common.FlowNavigator
import com.nanlagger.packinglist.features.roster.common.RosterTransitionAnimationHelper
import com.nanlagger.packinglist.features.roster.flow.di.RosterFlowComponentHolder
import com.nanlagger.packinglist.features.roster.flow.navigation.RosterFlowNavigator
import javax.inject.Inject

class RosterFlowFragment : BaseFlowFragment() {

    @Inject
    lateinit var flowNavigationHolder: NavigatorHolder
    @Inject
    lateinit var factory: RosterFlowViewModel.Factory
    @Inject
    lateinit var rosterTransitionAnimationHelper: RosterTransitionAnimationHelper

    private val viewModel: RosterFlowViewModel by viewModels { factory }
    override val navigationHolder: NavigatorHolder
        get() = flowNavigationHolder

    override val navigator: FlowNavigator by lazy { RosterFlowNavigator(this, rosterTransitionAnimationHelper) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RosterFlowComponentHolder.createOrGetComponent(screenName, parentScreenName).inject(this)
        flowNavigationHolder.removeNavigator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }

    override fun onBackPressed() {
        viewModel.back()
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        RosterFlowComponentHolder.deleteComponent(screenName)
    }

    companion object {
        fun newInstance(): RosterFlowFragment {
            return RosterFlowFragment()
        }
    }
}