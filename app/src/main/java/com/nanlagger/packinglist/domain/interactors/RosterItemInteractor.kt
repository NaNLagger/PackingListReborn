package com.nanlagger.packinglist.domain.interactors

import com.nanlagger.packinglist.domain.entities.RosterItem
import com.nanlagger.packinglist.domain.repository.RosterItemRepository
import io.reactivex.Completable
import io.reactivex.Scheduler

class RosterItemInteractor(
        private val rosterItemRepository: RosterItemRepository,
        private val mainScheduler: Scheduler,
        private val ioScheduler: Scheduler
) {

    fun addRosterItem(rosterItem: RosterItem): Completable {
        return rosterItemRepository.addItem(rosterItem)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }

    fun update(rosterItem: RosterItem): Completable {
        return rosterItemRepository.updateItem(rosterItem)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
    }
}