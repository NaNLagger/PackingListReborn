package com.nanlagger.note.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.nanlagger.database.BaseDao
import io.reactivex.Flowable

@Dao
interface NoteDao : BaseDao<NoteEntity> {

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME}")
    fun getNotes(): Flowable<List<NoteEntity>>

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} WHERE ${NoteEntity.ID} = :id")
    fun getNoteById(id: Long): Flowable<NoteEntity>

    @Update
    fun updateAll(entities: List<NoteEntity>)
}