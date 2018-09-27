package com.nanlagger.packinglist.domain.repository

import com.nanlagger.packinglist.domain.entities.Item
import com.nanlagger.packinglist.domain.entities.RosterItem
import io.reactivex.Observable


class RosterItemRepository() {

    fun getItems(rosterId: Long): Observable<List<RosterItem>> {
        return Observable.just(emptyList())
    }

    fun addItem(item: Item): Observable<Boolean> {
        return Observable.just(true)
    }

    fun deleteItem(id: Long): Observable<Boolean> {
        return Observable.just(true)
    }
}