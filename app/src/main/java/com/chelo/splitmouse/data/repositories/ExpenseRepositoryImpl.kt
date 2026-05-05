package com.chelo.splitmouse.data.repositories

import com.chelo.splitmouse.data.local.daos.ExpenseDao
import com.chelo.splitmouse.data.local.entities.toModel
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.domain.model.toEntity
import com.chelo.splitmouse.domain.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.exp

class ExpenseRepositoryImpl(private val expenseDao: ExpenseDao) : ExpenseRepository {


    override suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense.toEntity())
    }

    override fun getExpensesByEvent(eventId: Long): Flow<List<Expense>> {
        return expenseDao.getExpensesByEvent(eventId).map { list ->
            list.map {
                it.toModel()
            }
        }

    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense.toEntity())
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense.toEntity())
    }

    override suspend fun calculateTotalAmount(eventId: Long): Double {
        return expenseDao.calculateTotalAmount(eventId)
    }
}