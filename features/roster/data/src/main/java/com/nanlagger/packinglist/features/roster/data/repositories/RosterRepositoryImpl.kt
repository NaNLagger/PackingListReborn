package com.nanlagger.packinglist.features.roster.data.repositories

import com.nanlagger.packinglist.features.roster.data.dao.RosterDao
import com.nanlagger.packinglist.features.roster.domain.entities.Roster
import com.nanlagger.packinglist.features.roster.domain.entities.RosterItem
import com.nanlagger.packinglist.features.roster.domain.repositories.RosterRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class RosterRepositoryImpl(
    private val rosterDao: RosterDao
): RosterRepository {

    override fun getRosters(): Flowable<List<Roster>> {
        return rosterDao.getRostersWithItems()
            .map { rosterEntities ->
                rosterEntities.map { entity ->
                    Roster(entity.roster.id, entity.roster.name, entity.roster.priority, entity.items.map {
                        RosterItem(it.id, it.name, it.rosterId, it.isChecked)
                    })
                }
            }
    }

    override fun addRoster(roster: Roster): Completable {
        return Completable.fromCallable { rosterDao.insert(
            com.nanlagger.packinglist.features.roster.data.entities.RosterEntity(
                roster.id,
                roster.name,
                roster.priority
            )
        ) }
    }

    override fun deleteRoster(id: Long): Completable {
        return Completable.fromCallable { rosterDao.delete(
            com.nanlagger.packinglist.features.roster.data.entities.RosterEntity(
                id
            )
        ) }
    }

    override fun updateRosters(rosters: List<Roster>): Completable {
        val rosterEntities = rosters.map { roster ->
            com.nanlagger.packinglist.features.roster.data.entities.RosterEntity(
                roster.id,
                roster.name,
                roster.priority
            )
        }
        return Completable.fromCallable { rosterDao.updateAll(rosterEntities) }
    }

    override fun getRoster(id: Long): Flowable<Roster> {
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