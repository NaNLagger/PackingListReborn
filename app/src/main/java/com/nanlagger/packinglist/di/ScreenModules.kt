package com.nanlagger.packinglist.di

import android.arch.lifecycle.ViewModelProviders
import com.nanlagger.packinglist.domain.interactors.RosterInteractor
import com.nanlagger.packinglist.ui.main.MainActivity
import com.nanlagger.packinglist.ui.main.MainViewModel
import com.nanlagger.packinglist.ui.rosters.RosterListFragment
import com.nanlagger.packinglist.ui.rosters.RosterListViewModel
import com.nanlagger.packinglist.ui.rosters.adapter.RosterAdapter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val mainModule = Kodein.Module("main") {

    bind<MainViewModel.Factory>() with provider { MainViewModel.Factory(instance()) }

    bind<MainViewModel>() with provider {
        ViewModelProviders.of(instance<MainActivity>(), instance<MainViewModel.Factory>())
                .get(MainViewModel::class.java)
    }
}

val rosterListModule = Kodein.Module("rosterList") {

    bind<RosterInteractor>() with provider { RosterInteractor(instance(), instance("UI_THREAD"), instance("IO_THREAD")) }

    bind<RosterListViewModel.Factory>() with provider { RosterListViewModel.Factory(instance(), instance()) }

    bind<RosterListViewModel>() with provider {
        ViewModelProviders.of(instance<RosterListFragment>(), instance<RosterListViewModel.Factory>())
                .get(RosterListViewModel::class.java)
    }
}