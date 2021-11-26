package com.nanlagger.packinglist.features.roster.common

import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.View
import androidx.core.transition.addListener
import androidx.fragment.app.FragmentTransaction

class RosterTransitionAnimationHelper {

    private var containerSharedView: View? = null
    private var toolbarSharedView: View? = null
    private var titleSharedView: View? = null

    var endTransitionListener: (Long) -> Unit = {}

    fun setSharedName(rosterId: Long, container: View, toolbar: View, title: View) {
        container.transitionName = RosterSharedNames.SHARED_NAME_CONTAINER + rosterId
        toolbar.transitionName = RosterSharedNames.SHARED_NAME_TOOLBAR + rosterId
        title.transitionName = RosterSharedNames.SHARED_NAME_TITLE + rosterId
    }

    fun setupTransitionViews(container: View, toolbar: View, title: View) {
        containerSharedView = container
        toolbarSharedView = toolbar
        titleSharedView = title
    }

    fun setupTransition(rosterId: Long, fragmentTransaction: FragmentTransaction): TransitionSet {
        containerSharedView?.let { fragmentTransaction.addSharedElement(it, it.transitionName) }
        toolbarSharedView?.let { fragmentTransaction.addSharedElement(it, it.transitionName) }
        titleSharedView?.let { fragmentTransaction.addSharedElement(it, it.transitionName) }

        containerSharedView = null
        toolbarSharedView = null
        titleSharedView = null

        return getTransitionSet(rosterId)
    }

    private fun getTransitionSet(rosterId: Long): TransitionSet {
        return TransitionSet().apply {
            addTransition(ChangeTransform())
            addTransition(ChangeBounds())
            addListener(onEnd = { endTransitionListener(rosterId) })
        }
    }
}