package com.chelo.splitmouse.domain.repositories

import com.chelo.splitmouse.domain.model.Expense
import kotlinx.coroutines.flow.Flow


interface ExpenseRepository {


    suspend fun addExpense(expense: Expense)

    fun getExpensesByEvent(eventId: Long): Flow<List<Expense>>

    suspend fun deleteExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)

    suspend fun calculateTotalAmount(eventId: Long): Double

}