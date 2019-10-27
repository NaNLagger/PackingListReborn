package com.nanlagger.rosters.domain.interactors

import com.nanlagger.rosters.domain.repositories.RosterRepository
import com.nanlagger.rosters.domain.entities.Roster
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class RosterInteractor @Inject constructor(
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

    fun updateRoster(roster: Roster): Completable {
        return rosterRepository.updateRoster(roster)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun changePriority(rosters: List<Roster>): Completable {
        return rosterRepository.updateRosters(rosters)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun addRoster(roster: Roster): Single<Long> {
        return rosterRepository.addRoster(roster)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun deleteRoster(id: Long): Completable {
        return rosterRepository.deleteRoster(id)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun deleteRosters(ids: List<Long>): Completable {
        return rosterRepository.deleteRosters(ids)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }
}