package com.nanlagger.packinglist.features.roster.data.repositories

import com.nanlagger.packinglist.features.roster.data.dao.RosterItemDao
import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterItemRepository
import io.reactivex.Completable
import io.reactivex.Flowable


class RosterItemRepositoryImpl(
    private val rosterItemDao: RosterItemDao
): RosterItemRepository {

    override fun getItems(rosterId: Long): Flowable<List<RosterItem>> {
        return rosterItemDao.getItemsByRosterId(rosterId)
            .map { rosterItems ->
                rosterItems.map {
                    RosterItem(it.id, it.name, it.rosterId, it.isChecked)
                }
            }
    }

    override fun addItem(rosterItem: RosterItem): Completable {
        return Completable.fromCallable {
            rosterItemDao.insert(
                com.nanlagger.packinglist.features.roster.data.entities.RosterItemEntity(
                    0L,
                    rosterItem.name,
                    rosterItem.rosterId,
                    rosterItem.checked
                )
            )
        }
    }

    override fun deleteItem(id: Long): Completable {
        return Completable.fromCallable { rosterItemDao.delete(
            com.nanlagger.packinglist.features.roster.data.entities.RosterItemEntity(
                id
            )
        ) }
    }

    override fun updateItem(rosterItem: RosterItem): Completable {
        return Completable.fromCallable {
            rosterItemDao.update(
                com.nanlagger.packinglist.features.roster.data.entities.RosterItemEntity(
                    rosterItem.id,
                    rosterItem.name,
                    rosterItem.rosterId,
                    rosterItem.checked
                )
            )
        }
    }
}