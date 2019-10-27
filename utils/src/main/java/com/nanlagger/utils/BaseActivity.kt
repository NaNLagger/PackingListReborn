package com.nanlagger.utils

import android.support.v7.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.karumi.dexter.listener.single.BasePermissionListener

abstract class BaseActivity : AppCompatActivity() {

    protected fun checkPermission(permission: String, listener: (granted: Boolean) -> Unit) {
        Dexter.withActivity(this)
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
        Dexter.withActivity(this)
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