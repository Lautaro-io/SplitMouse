package com.chelo.splitmouse.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chelo.splitmouse.data.local.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ExpenseDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    fun getExpenses(): Flow<List<ExpenseEntity>>


    @Query("SELECT * FROM expenses WHERE eventId = :id")
    fun getExpensesByEvent(id: Long): Flow<List<ExpenseEntity>>

}