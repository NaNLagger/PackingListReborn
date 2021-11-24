package com.nanlagger.packinglist.features.roster.domain.repositories

import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import io.reactivex.Completable
import io.reactivex.Flowable

interface RosterItemRepository {
    fun getItems(rosterId: Long): Flowable<List<RosterItem>>
    fun addItem(rosterItem: RosterItem): Completable
    fun deleteItem(id: Long): Completable
    fun updateItem(rosterItem: RosterItem): Completable
}