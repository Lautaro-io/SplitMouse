package com.chelo.splitmouse.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chelo.splitmouse.data.local.entities.EventEntity


@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Query("SELECT * FROM events")
    suspend fun getEvents(): List<EventEntity>
}
