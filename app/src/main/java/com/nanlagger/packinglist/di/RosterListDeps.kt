package com.nanlagger.packinglist.di

import com.github.terrakok.cicerone.Router
import com.nanlagger.packinglist.data.database.dao.RosterDao

interface RosterListDeps : Dependencies {
    val rosterDao: RosterDao
    val router: Router
}