package com.nanlagger.packinglist.core.common

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import timber.log.Timber
import java.util.*

private const val STATE_SCREEN_UID = "STATE_SCREEN_UID"

abstract class BaseDialogFragment : DialogFragment() {

    private var instanceStateSaved: Boolean = false
    private var screenUID: String = ""
    val screenName: String
        get() = "${javaClass.simpleName}_$screenUID"

    protected open val parentScreenName: String by lazy {
        (parentFragment as? BaseFragment)?.screenName ?: (requireActivity() as? BaseActivity)?.screenName ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenUID = savedInstanceState?.getString(STATE_SCREEN_UID) ?: UUID.randomUUID().toString()
    }

    override fun onStart() {
        super.onStart()
        instanceStateSaved = false
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_SCREEN_UID, screenUID)
        instanceStateSaved = true
    }

    override fun onDestroy() {
        super.onDestroy()
        try {//We leave the screen and respectively all fragments will be destroyed
            if (requireActivity().isFinishing) {
                onFinallyFinished()
                return
            }

            // When we rotate device isRemoving() return true for fragment placed in backstack
            // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
            if (instanceStateSaved) {
                instanceStateSaved = false
                return
            }

            // See https://github.com/Arello-Mobile/Moxy/issues/24
            var anyParentIsRemoving = false
            var parent = parentFragment
            while (!anyParentIsRemoving && parent != null) {
                anyParentIsRemoving = parent.isRemoving
                parent = parent.parentFragment
            }

            if (isRemoving || anyParentIsRemoving) {
                onFinallyFinished()
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    protected open fun onFinallyFinished() {}
}