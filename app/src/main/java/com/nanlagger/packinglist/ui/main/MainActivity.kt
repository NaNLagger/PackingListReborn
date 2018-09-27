package com.nanlagger.packinglist.ui.main

import android.os.Bundle
import com.nanlagger.packinglist.R
import com.nanlagger.packinglist.di.mainModule
import com.nanlagger.packinglist.navigation.AppNavigator
import com.nanlagger.packinglist.ui.common.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.NavigatorHolder

class MainActivity : BaseActivity(), KodeinAware {
    private val _parentKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein)
        bind() from instance(this@MainActivity)
        import(mainModule)
    }

    private val navigationHolder by instance<NavigatorHolder>()
    private val navigator: AppNavigator by lazy {
        AppNavigator(this, supportFragmentManager, R.id.container_screen)
    }
    private val viewModel: MainViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}
