package com.nanlagger.packinglist.features.roster.data.repositories

import com.nanlagger.packinglist.features.roster.data.dao.RosterDao
import com.nanlagger.packinglist.features.roster.data.entities.RosterEntity
import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RosterRepositoryImpl(
    private val rosterDao: RosterDao
) : RosterRepository {

    override fun getRosters(): Flow<List<Roster>> {
        return rosterDao.getRostersWithItems()
            .map { rosterEntities ->
                rosterEntities.map { entity ->
                    Roster(entity.roster.id, entity.roster.name, entity.roster.priority, entity.items.map {
                        RosterItem(it.id, it.name, it.rosterId, it.isChecked)
                    })
                }
            }
    }

    override suspend fun addRoster(roster: Roster) {
        rosterDao.insert(
            RosterEntity(
                roster.id,
                roster.name,
                roster.priority
            )
        )
    }

    override suspend fun deleteRoster(id: Long) {
        rosterDao.delete(RosterEntity(id))
    }

    override suspend fun updateRosters(rosters: List<Roster>) {
        val rosterEntities = rosters.map { roster ->
            RosterEntity(
                roster.id,
                roster.name,
                roster.priority
            )
        }
        rosterDao.updateAll(rosterEntities)
    }

    override fun getRoster(id: Long): Flow<Roster> {
        return rosterDao.getRosterWithItems(id)
            .map { rosterWithItemsEntity ->
                Roster(rosterWithItemsEntity.roster.id,
                    rosterWithItemsEntity.roster.name,
                    rosterWithItemsEntity.roster.priority,
                    rosterWithItemsEntity.items.map {
                        RosterItem(it.id, it.name, it.rosterId, it.isChecked)
                    }
                )
            }
    }
}