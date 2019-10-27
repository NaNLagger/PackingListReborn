package com.nanlagger.packinglist.ui.main

import android.os.Bundle
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.di.Scopes
import com.nanlagger.packinglist.di.modules.MainModule
import com.nanlagger.packinglist.navigation.AppNavigator
import com.nanlagger.utils.BaseActivity
import com.nanlagger.utils.OnBackListener
import ru.terrakok.cicerone.NavigatorHolder
import toothpick.Scope
import toothpick.Toothpick

class MainActivity : BaseActivity() {

    private val navigationHolder: NavigatorHolder by lazy { scope.getInstance(NavigatorHolder::class.java) }
    private val navigator: AppNavigator by lazy {
        AppNavigator(this, supportFragmentManager, R.id.container_screen)
    }
    private val viewModel: MainViewModel by lazy { scope.getInstance(MainViewModel::class.java) }
    private lateinit var scope: Scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope = Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.MAIN_SCOPE)
                .apply {
                    installModules(MainModule(this@MainActivity))
                }
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

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container_screen)
        if (fragment is OnBackListener) {
            fragment.onBack()
        } else {
            viewModel.back()
        }
    }
}
