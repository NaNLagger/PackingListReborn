package com.nanlagger.packinglist.features.roster.domain.repositories

import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import kotlinx.coroutines.flow.Flow

interface RosterItemRepository {
    fun getItems(rosterId: Long): Flow<List<RosterItem>>
    suspend fun addItem(rosterItem: RosterItem)
    suspend fun deleteItem(id: Long)
    suspend fun updateItem(rosterItem: RosterItem)
}