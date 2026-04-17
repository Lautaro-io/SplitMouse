package com.chelo.splitmouse.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chelo.splitmouse.data.local.entities.ParticipantEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ParticipantDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipant(participant: ParticipantEntity)

    @Query("SELECT * FROM participants where eventId = :eventId")
    fun getParticipantsByEvent(eventId : Long): Flow<List<ParticipantEntity>>





}
