package com.nanlagger.packinglist.features.roster.domain.interactors

import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterItemRepository
import io.reactivex.Completable

class RosterItemInteractor(
    private val rosterItemRepository: RosterItemRepository
) {

    fun addRosterItem(rosterItem: RosterItem): Completable {
        return rosterItemRepository.addItem(rosterItem)
    }

    fun update(rosterItem: RosterItem): Completable {
        return rosterItemRepository.updateItem(rosterItem)
    }
}