package com.nanlagger.packinglist.domain.repository

import com.nanlagger.packinglist.data.database.dao.RosterDao
import com.nanlagger.packinglist.data.database.entities.RosterEntity
import com.nanlagger.packinglist.domain.entities.Roster
import com.nanlagger.packinglist.domain.entities.RosterItem
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class RosterRepository @Inject constructor(
    private val rosterDao: RosterDao
) {

    fun getRosters(): Flowable<List<Roster>> {
        return rosterDao.getRostersWithItems()
            .map { rosterEntities ->
                rosterEntities.map { entity ->
                    Roster(entity.roster.id, entity.roster.name, entity.roster.priority, entity.items.map {
                        RosterItem(it.id, it.name, it.rosterId, it.isChecked)
                    })
                }
            }
    }

    fun addRoster(roster: Roster): Completable {
        return Completable.fromCallable { rosterDao.insert(RosterEntity(roster.id, roster.name, roster.priority)) }
    }

    fun deleteRoster(id: Long): Completable {
        return Completable.fromCallable { rosterDao.delete(RosterEntity(id)) }
    }

    fun updateRosters(rosters: List<Roster>): Completable {
        val rosterEntities = rosters.map { roster -> RosterEntity(roster.id, roster.name, roster.priority) }
        return Completable.fromCallable { rosterDao.updateAll(rosterEntities) }
    }

    fun getRoster(id: Long): Flowable<Roster> {
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