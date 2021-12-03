package com.nanlagger.packinglist.features.roster.data.repositories

import com.nanlagger.packinglist.features.roster.data.dao.RosterItemDao
import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class RosterItemRepositoryImpl(
    private val rosterItemDao: RosterItemDao
) : RosterItemRepository {

    override fun getItems(rosterId: Long): Flow<List<RosterItem>> {
        return rosterItemDao.getItemsByRosterId(rosterId)
            .map { rosterItems ->
                rosterItems.map {
                    RosterItem(it.id, it.name, it.rosterId, it.isChecked)
                }
            }
    }

    override suspend fun addItem(rosterItem: RosterItem) {
        rosterItemDao.insert(
            com.nanlagger.packinglist.features.roster.data.entities.RosterItemEntity(
                0L,
                rosterItem.name,
                rosterItem.rosterId,
                rosterItem.checked
            )
        )
    }

    override suspend fun deleteItem(id: Long) {
        rosterItemDao.delete(
            com.nanlagger.packinglist.features.roster.data.entities.RosterItemEntity(
                id
            )
        )
    }

    override suspend fun updateItem(rosterItem: RosterItem) {
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