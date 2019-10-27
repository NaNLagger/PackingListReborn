package com.nanlagger.rosters.domain.repositories

import com.nanlagger.rosters.data.database.dao.RosterDao
import com.nanlagger.rosters.data.database.entities.RosterEntity
import com.nanlagger.rosters.domain.entities.Roster
import com.nanlagger.rosters.domain.entities.RosterItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RosterRepository @Inject constructor(
        private val rosterDao: RosterDao
) {

    fun getRosters(): Flowable<List<Roster>> {
        return rosterDao.getRostersWithItems()
                .map { rosterEntities ->
                    rosterEntities.map { entity ->
                        Roster(entity.roster.id, entity.roster.priority, entity.roster.name, entity.items.map {
                            RosterItem(it.id, it.name, it.rosterId, it.isChecked) })
                    }
                }
    }

    fun addRoster(roster: Roster): Single<Long> {
        return Single.fromCallable { rosterDao.insert(RosterEntity(roster.id, roster.name, roster.priority)) }
    }

    fun deleteRoster(id: Long): Completable {
        return Completable.fromCallable { rosterDao.delete(RosterEntity(id)) }
    }

    fun deleteRosters(ids: List<Long>): Completable {
        return Completable.fromCallable { rosterDao.delete(ids.map { RosterEntity(it) }) }
    }

    fun updateRosters(rosters: List<Roster>): Completable {
        val rosterEntities = rosters.map { roster -> RosterEntity(roster.id, roster.name, roster.priority) }
        return Completable.fromCallable { rosterDao.updateAll(rosterEntities) }
    }

    fun getRoster(id: Long): Flowable<Roster> {
        return rosterDao.getRosterWithItems(id)
                .map { rosterWithItemsEntity ->
                    Roster(rosterWithItemsEntity.roster.id,
                            rosterWithItemsEntity.roster.priority,
                            rosterWithItemsEntity.roster.name,
                            rosterWithItemsEntity.items.map {
                                RosterItem(it.id, it.name, it.rosterId, it.isChecked)
                            }
                    )
                }
    }

    fun updateRoster(roster: Roster): Completable {
        return Completable.fromCallable { rosterDao.update(RosterEntity(roster.id, roster.name, roster.priority)) }
    }
}