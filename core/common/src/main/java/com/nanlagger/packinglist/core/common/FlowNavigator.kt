package com.nanlagger.packinglist.core.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator

open class FlowNavigator(private val fragment: BaseFlowFragment) : AppNavigator(
    fragment.requireActivity(),
    fragment.containerId,
    fragment.childFragmentManager
) {
    override fun applyCommand(command: Command) {
        hideKeyboard() //force close keyboard on SAMSUNG!!! device
        super.applyCommand(command)
    }

    private fun hideKeyboard() {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var currentFocus = activity.currentFocus
        if (currentFocus == null)
            currentFocus = activity.findViewById<View>(android.R.id.content).rootView
        if (currentFocus != null)
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}