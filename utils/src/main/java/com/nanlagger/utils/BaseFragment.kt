package com.nanlagger.utils

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.karumi.dexter.listener.single.BasePermissionListener
import toothpick.Scope
import toothpick.Toothpick
import java.util.*

private const val KEY_FRAGMENT_ID = "key.fragment.id"

abstract class BaseFragment : Fragment() {
    abstract val layoutId: Int

    private var isInstanceStateSaved = false
    protected lateinit var fragmentId: String
    protected lateinit var scope: Scope
    protected lateinit var scopeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentId = savedInstanceState?.getString(KEY_FRAGMENT_ID) ?: UUID.randomUUID().toString()
        scopeName = this::class.java.simpleName + fragmentId
        scope = onOpenScope()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(layoutId, container, false)!!

    override fun onStart() {
        super.onStart()
        isInstanceStateSaved = false
    }

    override fun onResume() {
        super.onResume()
        isInstanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        isInstanceStateSaved = true
        outState.putString(KEY_FRAGMENT_ID, fragmentId)
    }

    override fun onDestroy() {
        super.onDestroy()
        //We leave the screen and respectively all fragments will be destroyed
        if (activity?.isFinishing == true) {
            onCloseScope()
            return
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (isInstanceStateSaved) {
            isInstanceStateSaved = false
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
            onCloseScope()
        }
    }

    protected open fun onOpenScope(): Scope {
        return Toothpick.openScope(scopeName)
    }

    protected open fun onCloseScope() {
        Toothpick.closeScope(scopeName)
    }

    protected fun showSnackMessage(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        view?.let {
            val snackbar = Snackbar.make(it, message, duration)
            snackbar.show()
        }
    }

    protected fun checkPermission(permission: String, listener: (granted: Boolean) -> Unit) {
        Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        super.onPermissionGranted(response)
                        listener(true)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        super.onPermissionDenied(response)
                        listener(false)
                    }
                })
                .check()
    }

    protected fun checkPermissions(vararg permissions: String, listener: (granted: Boolean) -> Unit) {
        Dexter.withActivity(activity)
                .withPermissions(*permissions)
                .withListener(object : BaseMultiplePermissionsListener() {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        super.onPermissionsChecked(report)
                        listener(report?.areAllPermissionsGranted() ?: false)
                    }
                })
                .check()
    }
}