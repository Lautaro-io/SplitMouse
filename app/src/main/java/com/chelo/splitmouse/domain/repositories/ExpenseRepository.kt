package com.chelo.splitmouse.domain.repositories

import com.chelo.splitmouse.domain.model.Expense
import kotlinx.coroutines.flow.Flow


interface ExpenseRepository {


    suspend fun addExpense(expense: Expense)

    fun getExpensesByEvent(eventId: Long): Flow<List<Expense>>

}