package com.chelo.splitmouse.domain.repositories



interface Expense {


    suspend fun addExpense(expense: Expense)

    suspend fun getExpensesByEvent(eventId: Long): List<Expense>

}