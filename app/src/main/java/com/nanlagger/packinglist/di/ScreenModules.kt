package com.nanlagger.packinglist.di

import android.arch.lifecycle.ViewModelProviders
import com.nanlagger.packinglist.ui.main.MainActivity
import com.nanlagger.packinglist.ui.main.MainViewModel
import com.nanlagger.packinglist.ui.rosters.RostersListFragment
import com.nanlagger.packinglist.ui.rosters.RostersListViewModel
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

val rostersListModule = Kodein.Module("rostersList") {

    bind<RostersListViewModel.Factory>() with provider { RostersListViewModel.Factory(instance(), instance()) }

    bind<RostersListViewModel>() with provider {
        ViewModelProviders.of(instance<RostersListFragment>(), instance<RostersListViewModel.Factory>())
                .get(RostersListViewModel::class.java)
    }

    bind<RosterAdapter>() with provider {
        RosterAdapter(instance<RostersListViewModel>()::openRoster)
    }
}