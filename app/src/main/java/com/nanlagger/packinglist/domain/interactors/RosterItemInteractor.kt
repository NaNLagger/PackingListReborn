package com.nanlagger.packinglist.domain.interactors

import com.nanlagger.packinglist.domain.entities.RosterItem
import com.nanlagger.packinglist.domain.repository.RosterItemRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class RosterItemInteractor @Inject constructor(
    private val rosterItemRepository: RosterItemRepository
) {

    fun addRosterItem(rosterItem: RosterItem): Completable {
        return rosterItemRepository.addItem(rosterItem)
    }

    fun update(rosterItem: RosterItem): Completable {
        return rosterItemRepository.updateItem(rosterItem)
    }
}