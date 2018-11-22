package com.nanlagger.packinglist.domain.interactors

import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.domain.repository.RosterRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler

class RosterInteractor(
        private val rosterRepository: RosterRepository,
        private val mainScheduler: Scheduler,
        private val ioScheduler: Scheduler
) {

    fun getRoster(id: Long): Flowable<Roster> {
        return rosterRepository.getRoster(id)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun getRosters(): Flowable<List<Roster>> {
        return rosterRepository.getRosters()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun changePriority(rosters: List<Roster>): Completable {
        return rosterRepository.updateRosters(rosters)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun addRoster(roster: Roster): Completable {
        return rosterRepository.addRoster(roster)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun deleteRoster(id: Long): Completable {
        return rosterRepository.deleteRoster(id)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }
}