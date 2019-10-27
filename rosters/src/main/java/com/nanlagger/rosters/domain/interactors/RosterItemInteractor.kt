package com.nanlagger.rosters.domain.interactors

import com.nanlagger.rosters.domain.repositories.RosterItemRepository
import com.nanlagger.rosters.domain.entities.RosterItem
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class RosterItemInteractor @Inject constructor(
        private val rosterItemRepository: RosterItemRepository,
        private val mainScheduler: Scheduler,
        private val ioScheduler: Scheduler
) {

    fun addRosterItem(rosterItem: RosterItem): Completable {
        return rosterItemRepository.addItem(rosterItem)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun updateRosterItem(rosterItem: RosterItem): Completable {
        return rosterItemRepository.updateItem(rosterItem)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun deleteRosterItem(rosterItem: RosterItem): Completable {
        return rosterItemRepository.deleteItem(rosterItem.id)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }
}