package com.chelo.splitmouse.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chelo.splitmouse.data.local.daos.EventDao
import com.chelo.splitmouse.data.local.daos.ExpenseDao
import com.chelo.splitmouse.data.local.daos.ParticipantDao
import com.chelo.splitmouse.data.local.entities.EventEntity
import com.chelo.splitmouse.data.local.entities.ExpenseEntity
import com.chelo.splitmouse.data.local.entities.ParticipantEntity

@Database(
    entities = [ExpenseEntity::class, ParticipantEntity::class , EventEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun participantDao(): ParticipantDao
    abstract fun eventDao(): EventDao

    companion object{
        fun getInstance(context: Context): AppDatabase {
            return  Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database"
            ).build(
            )
        }

    }
}



