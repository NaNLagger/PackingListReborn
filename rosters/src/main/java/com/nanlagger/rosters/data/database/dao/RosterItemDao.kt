package com.nanlagger.rosters.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.nanlagger.database.BaseDao
import com.nanlagger.rosters.data.database.entities.RosterItemEntity
import io.reactivex.Flowable

@Dao
interface RosterItemDao : BaseDao<RosterItemEntity> {

    @Query("SELECT * FROM ${RosterItemEntity.TABLE_NAME} WHERE ${RosterItemEntity.ROSTER_ID} = :rosterId")
    fun getItemsByRosterId(rosterId: Long): Flowable<List<RosterItemEntity>>
}