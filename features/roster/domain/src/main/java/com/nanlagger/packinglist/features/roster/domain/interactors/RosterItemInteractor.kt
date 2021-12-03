package com.nanlagger.packinglist.features.roster.domain.interactors

import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterItemRepository

class RosterItemInteractor(
    private val rosterItemRepository: RosterItemRepository
) {

    suspend fun addRosterItem(rosterItem: RosterItem) {
        return rosterItemRepository.addItem(rosterItem)
    }

    suspend fun update(rosterItem: RosterItem) {
        return rosterItemRepository.updateItem(rosterItem)
    }
}