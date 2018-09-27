package com.nanlagger.packinglist.ui.common

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

abstract class BaseFragment : Fragment() {
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(layoutId, container, false)!!

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