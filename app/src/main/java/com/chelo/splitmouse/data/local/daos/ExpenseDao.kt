package com.chelo.splitmouse.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    @Query("SELECT SUM(amount) FROM expenses WHERE eventId = :eventId")
    suspend fun calculateTotalAmount(eventId: Long): Double


    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)
}