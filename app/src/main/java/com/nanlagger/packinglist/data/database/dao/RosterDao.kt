package com.nanlagger.packinglist.data.database.dao

import android.arch.persistence.room.*
import com.nanlagger.packinglist.data.database.entities.RosterEntity
import com.nanlagger.packinglist.data.database.entities.RosterItemEntity
import com.nanlagger.packinglist.data.database.entities.RosterWithInfoEntity
import com.nanlagger.packinglist.data.database.entities.RosterWithItemsEntity
import io.reactivex.Flowable

@Dao
interface RosterDao : BaseDao<RosterEntity> {

    @Query("SELECT * FROM ${RosterEntity.TABLE_NAME}")
    fun getRosters(): Flowable<List<RosterEntity>>

    @Query("SELECT * FROM ${RosterEntity.TABLE_NAME} WHERE ${RosterEntity.ID} = :id")
    fun getRosterById(id: Long): Flowable<RosterEntity>

    @Query("SELECT ${RosterEntity.ID}, ${RosterEntity.NAME}, ${RosterEntity.PRIORITY}, " +
            "(SELECT count(${RosterItemEntity.ID}) FROM ${RosterItemEntity.TABLE_NAME} WHERE ${RosterItemEntity.ROSTER_ID} = ${RosterEntity.TABLE_NAME}.${RosterEntity.ID}) as countItems, " +
            "(SELECT count(${RosterItemEntity.ID}) FROM ${RosterItemEntity.TABLE_NAME} WHERE ${RosterItemEntity.ROSTER_ID} = ${RosterEntity.TABLE_NAME}.${RosterEntity.ID} AND ${RosterItemEntity.IS_CHECKED} = 1) as checkedItems " +
            "FROM ${RosterEntity.TABLE_NAME}")
    fun getRostersWithInfo(): Flowable<List<RosterWithInfoEntity>>

    @Transaction
    @Query("SELECT * FROM ${RosterEntity.TABLE_NAME}")
    fun getRostersWithItems(): Flowable<List<RosterWithItemsEntity>>

    @Transaction
    @Query("SELECT * FROM ${RosterEntity.TABLE_NAME} WHERE ${RosterEntity.ID} = :id")
    fun getRosterWithItems(id: Long): Flowable<RosterWithItemsEntity>

    @Update
    fun updateAll(rosterEntities: List<RosterEntity>)

}