package com.chelo.splitmouse.domain.repositories

import com.chelo.splitmouse.domain.model.Expense


interface ExpenseRepository {


    suspend fun addExpense(expense: Expense)

    suspend fun getExpensesByEvent(eventId: Long): List<Expense>

}