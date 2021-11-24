package com.nanlagger.packinglist.core.common

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val STATE_SCREEN_UID = "STATE_SCREEN_UID"

abstract class BaseActivity : AppCompatActivity() {

    private var screenUID: String = ""
    val screenName: String
        get() = "${javaClass.simpleName}_$screenUID"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        screenUID = savedInstanceState?.getString(STATE_SCREEN_UID) ?: UUID.randomUUID().toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_SCREEN_UID, screenUID)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            onFinallyFinished()
        }
    }

    protected open fun onFinallyFinished() {

    }
}