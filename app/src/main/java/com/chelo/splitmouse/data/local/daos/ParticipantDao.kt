package com.chelo.splitmouse.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.chelo.splitmouse.data.local.entities.ParticipantEntity


@Dao
interface ParticipantDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipant(participant: ParticipantEntity)




}
