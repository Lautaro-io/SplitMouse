package com.chelo.splitmouse.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.chelo.splitmouse.data.local.entities.EventEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity) : Long


    @Update
    suspend fun updateEvent(event: EventEntity)

    @Query("SELECT * FROM events ORDER BY id DESC")
    fun getEvents(): Flow<List<EventEntity>>


    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Long): Flow<EventEntity?>


}
