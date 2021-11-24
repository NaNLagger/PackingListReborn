package com.nanlagger.packinglist.features.main.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.github.terrakok.cicerone.NavigatorHolder
import com.nanlagger.packinglist.core.common.BaseActivity
import com.nanlagger.packinglist.core.common.OnBackPressedListener
import com.nanlagger.packinglist.features.main.R
import com.nanlagger.packinglist.features.main.di.MainComponentHolder
import com.nanlagger.packinglist.features.main.navigation.AppNavigator
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var navigationHolder: NavigatorHolder
    @Inject
    lateinit var factory: MainViewModel.Factory

    private val navigator: AppNavigator by lazy {
        AppNavigator(this, R.id.container_screen)
    }
    private val viewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainComponentHolder.createOrGetComponent(screenName).inject(this)
        setContentView(R.layout.activity_main)
        navigationHolder.removeNavigator()
        viewModel.init()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        MainComponentHolder.deleteComponent(screenName)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container_screen) ?: viewModel.back()
        if (fragment is OnBackPressedListener) {
            fragment.onBackPressed()
        } else {
            viewModel.back()
        }
    }
}
