package com.nanlagger.packinglist.features.roster.domain.interactors

import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository
import kotlinx.coroutines.flow.Flow

class RosterInteractor(
    private val rosterRepository: RosterRepository
) {

    fun getRoster(id: Long): Flow<Roster> {
        return rosterRepository.getRoster(id)
    }

    fun getRosters(): Flow<List<Roster>> {
        return rosterRepository.getRosters()
    }

    suspend fun changePriority(rosters: List<Roster>) {
        return rosterRepository.updateRosters(rosters)
    }

    suspend fun addRoster(roster: Roster) {
        return rosterRepository.addRoster(roster)
    }

    suspend fun deleteRoster(id: Long) {
        return rosterRepository.deleteRoster(id)
    }
}