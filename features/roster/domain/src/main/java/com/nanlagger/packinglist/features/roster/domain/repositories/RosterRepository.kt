package com.nanlagger.packinglist.features.roster.domain.repositories

import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import kotlinx.coroutines.flow.Flow

interface RosterRepository {
    fun getRosters(): Flow<List<Roster>>
    suspend fun addRoster(roster: Roster)
    suspend fun deleteRoster(id: Long)
    suspend fun updateRosters(rosters: List<Roster>)
    fun getRoster(id: Long): Flow<Roster>
}