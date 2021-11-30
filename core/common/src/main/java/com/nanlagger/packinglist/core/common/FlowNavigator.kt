package com.nanlagger.packinglist.core.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

open class FlowNavigator(private val fragment: BaseFlowFragment) : AppNavigator(
    fragment.requireActivity(),
    fragment.containerId,
    fragment.childFragmentManager
) {
    override fun applyCommand(command: Command) {
        hideKeyboard() //force close keyboard on SAMSUNG!!! device
        super.applyCommand(command)
    }

    override fun commitNewFragmentScreen(screen: FragmentScreen, addToBackStack: Boolean) {
        val fragment = screen.createFragment(fragmentFactory)
        val transaction = fragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true)
        setupFragmentTransaction(
            screen,
            transaction,
            fragmentManager.findFragmentById(containerId),
            fragment
        )
        when {
            fragment is DialogFragment -> transaction.add(fragment, screen.screenKey)
            screen.clearContainer -> transaction.replace(containerId, fragment, screen.screenKey)
            else -> transaction.add(containerId, fragment, screen.screenKey)
        }
        if (addToBackStack) {
            transaction.addToBackStack(screen.screenKey)
            localStackCopy.add(screen.screenKey)
        }
        transaction.commit()
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