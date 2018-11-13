package com.nanlagger.packinglist.domain.interactors

import com.nanlagger.packinglist.domain.entities.Roster
import io.reactivex.Completable
import io.reactivex.Observable

class RosterInteractor() {

    fun getRosters(): Observable<List<Roster>> {
        val listOf = listOf(
                Roster(0, "Test Roster 1", 0, emptyList()),
                Roster(1, "Test Roster 2", 1, emptyList()),
                Roster(2, "Test Roster 3", 2, emptyList()),
                Roster(3, "Test Roster 4", 3, emptyList()),
                Roster(4, "Test Roster 5", 4, emptyList()),
                Roster(5, "Test Roster 6", 5, emptyList()),
                Roster(6, "Test Roster 7", 6, emptyList()),
                Roster(7, "Test Roster 8", 7, emptyList()),
                Roster(8, "Test Roster 9", 8, emptyList()),
                Roster(9, "Test Roster 10", 9, emptyList()),
                Roster(10, "Test Roster 11", 10, emptyList())
        )
        return Observable.just(listOf)
    }

    fun changePriority(rosters: List<Roster>): Completable {
        return Completable.complete()
    }

    fun addRoster(roster: Roster): Completable {
        return Completable.complete()
    }

    fun deleteRoster(id: Long): Completable {
        return Completable.complete()
    }
}