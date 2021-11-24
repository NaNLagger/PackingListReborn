package com.nanlagger.packinglist.features.roster.domain.interactors

import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class RosterInteractor(
    private val rosterRepository: RosterRepository
) {

    fun getRoster(id: Long): Flowable<Roster> {
        return rosterRepository.getRoster(id)
    }

    fun getRosters(): Flowable<List<Roster>> {
        return rosterRepository.getRosters()
    }

    fun changePriority(rosters: List<Roster>): Completable {
        return rosterRepository.updateRosters(rosters)
    }

    fun addRoster(roster: Roster): Completable {
        return rosterRepository.addRoster(roster)
    }

    fun deleteRoster(id: Long): Completable {
        return rosterRepository.deleteRoster(id)
    }
}