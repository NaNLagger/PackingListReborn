package com.nanlagger.packinglist.di

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.data.database.dao.RosterDao
import com.nanlagger.packinglist.data.database.dao.RosterItemDao

interface MainDeps: Dependencies {
    val navigationHolder: NavigatorHolder
    val router: Router
    val rosterDao: RosterDao
    val rosterItemDao: RosterItemDao
}