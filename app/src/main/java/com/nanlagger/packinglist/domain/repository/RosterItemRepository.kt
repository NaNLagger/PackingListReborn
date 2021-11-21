package com.nanlagger.packinglist.domain.repository

import com.nanlagger.packinglist.data.database.dao.RosterItemDao
import com.nanlagger.packinglist.data.database.entities.RosterItemEntity
import com.nanlagger.packinglist.domain.entities.RosterItem
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject


class RosterItemRepository @Inject constructor(
    private val rosterItemDao: RosterItemDao
) {

    fun getItems(rosterId: Long): Flowable<List<RosterItem>> {
        return rosterItemDao.getItemsByRosterId(rosterId)
            .map { rosterItems ->
                rosterItems.map {
                    RosterItem(it.id, it.name, it.rosterId, it.isChecked)
                }
            }
    }

    fun addItem(rosterItem: RosterItem): Completable {
        return Completable.fromCallable {
            rosterItemDao.insert(
                RosterItemEntity(
                    0L,
                    rosterItem.name,
                    rosterItem.rosterId,
                    rosterItem.checked
                )
            )
        }
    }

    fun deleteItem(id: Long): Completable {
        return Completable.fromCallable { rosterItemDao.delete(RosterItemEntity(id)) }
    }

    fun updateItem(rosterItem: RosterItem): Completable {
        return Completable.fromCallable {
            rosterItemDao.update(
                RosterItemEntity(
                    rosterItem.id,
                    rosterItem.name,
                    rosterItem.rosterId,
                    rosterItem.checked
                )
            )
        }
    }
}