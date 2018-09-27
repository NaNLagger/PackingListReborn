package com.nanlagger.packinglist.domain.repository

import com.nanlagger.packinglist.domain.entities.Roster
import io.reactivex.Observable

class RosterRepository() {

    fun getRosters(): Observable<List<Roster>> {
        return Observable.just(emptyList())
    }

    fun addRoster(roster: Roster): Observable<Boolean> {
        return Observable.just(true)
    }

    fun deleteRoster(id: Long): Observable<Boolean> {
        return Observable.just(true)
    }
}