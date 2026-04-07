package com.chelo.splitmouse.data.repositories

import com.chelo.splitmouse.data.local.daos.ExpenseDao
import com.chelo.splitmouse.data.local.entities.toModel
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.domain.model.toEntity
import com.chelo.splitmouse.domain.repositories.ExpenseRepository

class ExpenseRepositoryImpl(private val expenseDao: ExpenseDao) : ExpenseRepository {


    override suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense.toEntity())
    }

    override suspend fun getExpensesByEvent(eventId: Long): List<Expense> {
        return expenseDao.getExpenses().map {
            it.toModel()
        }.filter {
            it.eventId == eventId
        }

    }
}