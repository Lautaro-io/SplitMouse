package com.chelo.splitmouse.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chelo.splitmouse.data.local.entities.ExpenseEntity


@Dao
interface ExpenseDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    suspend fun getExpenses(): List<ExpenseEntity>

}