package com.nanlagger.packinglist.core.common

import android.content.res.Resources
import androidx.annotation.ArrayRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes

class ResourcesProvider(private val resources: Resources) {

    fun getString(@StringRes resId: Int): String {
        return resources.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return resources.getString(resId, formatArgs)
    }

    fun getArray(@ArrayRes resId: Int): Array<String> {
        return resources.getStringArray(resId)
    }

    fun getDimen(@DimenRes resId: Int): Int {
        return resources.getDimensionPixelSize(resId)
    }
}