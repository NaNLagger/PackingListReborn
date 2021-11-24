package com.nanlagger.packinglist.features.roster.domain.repositories

import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import io.reactivex.Completable
import io.reactivex.Flowable

interface RosterRepository {
    fun getRosters(): Flowable<List<Roster>>
    fun addRoster(roster: Roster): Completable
    fun deleteRoster(id: Long): Completable
    fun updateRosters(rosters: List<Roster>): Completable
    fun getRoster(id: Long): Flowable<Roster>
}