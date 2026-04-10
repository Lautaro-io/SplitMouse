package com.chelo.splitmouse.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chelo.splitmouse.data.local.entities.EventEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Query("SELECT * FROM events")
    fun getEvents(): Flow<List<EventEntity>>
}
