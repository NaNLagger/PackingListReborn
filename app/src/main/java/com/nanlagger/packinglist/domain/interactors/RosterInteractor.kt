package com.nanlagger.packinglist.domain.interactors

import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.domain.repository.RosterRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import javax.inject.Inject

class RosterInteractor @Inject constructor(
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