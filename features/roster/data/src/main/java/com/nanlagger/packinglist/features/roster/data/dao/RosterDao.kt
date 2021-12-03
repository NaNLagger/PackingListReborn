package com.nanlagger.packinglist.features.roster.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.nanlagger.packinglist.core.common.database.BaseDao
import com.nanlagger.packinglist.features.roster.data.entities.RosterEntity
import com.nanlagger.packinglist.features.roster.data.entities.RosterItemEntity
import com.nanlagger.packinglist.features.roster.data.entities.RosterWithInfoEntity
import com.nanlagger.packinglist.features.roster.data.entities.RosterWithItemsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RosterDao : BaseDao<RosterEntity> {

    @Query("SELECT * FROM ${RosterEntity.TABLE_NAME}")
    fun getRosters(): Flow<List<RosterEntity>>

    @Query("SELECT * FROM ${RosterEntity.TABLE_NAME} WHERE ${RosterEntity.ID} = :id")
    fun getRosterById(id: Long): Flow<RosterEntity>

    @Query("SELECT ${RosterEntity.ID}, ${RosterEntity.NAME}, ${RosterEntity.PRIORITY}, " +
            "(SELECT count(${RosterItemEntity.ID}) FROM ${RosterItemEntity.TABLE_NAME} WHERE ${RosterItemEntity.ROSTER_ID} = ${RosterEntity.TABLE_NAME}.${RosterEntity.ID}) as countItems, " +
            "(SELECT count(${RosterItemEntity.ID}) FROM ${RosterItemEntity.TABLE_NAME} WHERE ${RosterItemEntity.ROSTER_ID} = ${RosterEntity.TABLE_NAME}.${RosterEntity.ID} AND ${RosterItemEntity.IS_CHECKED} = 1) as checkedItems " +
            "FROM ${RosterEntity.TABLE_NAME}")
    fun getRostersWithInfo(): Flow<List<RosterWithInfoEntity>>

    @Transaction
    @Query("SELECT * FROM ${RosterEntity.TABLE_NAME}")
    fun getRostersWithItems(): Flow<List<RosterWithItemsEntity>>

    @Transaction
    @Query("SELECT * FROM ${RosterEntity.TABLE_NAME} WHERE ${RosterEntity.ID} = :id")
    fun getRosterWithItems(id: Long): Flow<RosterWithItemsEntity>

    @Update
    suspend fun updateAll(rosterEntities: List<RosterEntity>)
}