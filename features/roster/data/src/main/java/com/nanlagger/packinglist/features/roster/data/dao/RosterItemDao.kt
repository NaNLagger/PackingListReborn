package com.nanlagger.packinglist.features.roster.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.nanlagger.packinglist.core.common.database.BaseDao
import com.nanlagger.packinglist.features.roster.data.entities.RosterItemEntity
import io.reactivex.Flowable

@Dao
interface RosterItemDao : BaseDao<RosterItemEntity> {

    @Query("SELECT * FROM ${RosterItemEntity.TABLE_NAME} WHERE ${RosterItemEntity.ROSTER_ID} = :rosterId")
    fun getItemsByRosterId(rosterId: Long): Flowable<List<RosterItemEntity>>
}