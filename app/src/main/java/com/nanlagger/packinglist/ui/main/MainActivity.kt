package com.nanlagger.packinglist.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.github.terrakok.cicerone.NavigatorHolder
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.di.MainComponentHolder
import com.nanlagger.packinglist.navigation.AppNavigator
import com.nanlagger.packinglist.ui.common.BaseActivity
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

    override fun onDestroy() {
        super.onDestroy()
        MainComponentHolder.deleteComponent(screenName)
    }
}
