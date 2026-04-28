package com.chelo.splitmouse.domain.usecases

import android.util.Log
import com.chelo.splitmouse.domain.model.Balance
import com.chelo.splitmouse.domain.model.Debt
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.domain.model.Participant
import kotlin.math.abs

class GetEventSettlementUseCase {


    operator fun invoke(participants: List<Participant>, expenses: List<Expense>): List<Debt> {

        val totalAmount = expenses.sumOf { it.amount }
        val quote = totalAmount / participants.size
        val balances = participants.map { participant ->
            val totalExpense = expenses.filter { it.payerId == participant.id }.sumOf { it.amount }
            val balance = totalExpense - quote
            Balance(participant.name, totalExpense, balance)
        }.toMutableList()

        val debts = balances.filter { it.balance < 0 }.toMutableList()
        val creditors = balances.filter { it.balance > 0 }.toMutableList()
        val listDebts = mutableListOf<Debt>()

        while (debts.isNotEmpty() && creditors.isNotEmpty()) {
            val debtor = debts[0]
            val creditor = creditors[0]


            val debtAmount = -(debtor.balance)

            val creditorBalance = creditor.balance

            val amountToPay = minOf(debtAmount, creditorBalance)

            listDebts.add(
                Debt(
                    from = debtor.participantName,
                    to = creditor.participantName,
                    amount = amountToPay
                )
            )

            debtor.balance += amountToPay
            creditor.balance -= amountToPay

            if (abs(debtor.balance) < 0.1) debts.removeAt(0)
            if (abs(creditor.balance) < 0.1) creditors.removeAt(0)
        }


        return listDebts
    }
}