package com.example.budgettracking.viewmodel

import androidx.lifecycle.ViewModel
import com.example.budgettracking.data.model.Transaction
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.data.repository.FakeTransactionRepository
import com.example.budgettracking.data.repository.TransactionRepository
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val repository: TransactionRepository =
        FakeTransactionRepository()

    val transactions: StateFlow<List<Transaction>> =
        repository.transactions

    fun addIncome(title: String, amount: Double) {
        repository.addTransaction(
            Transaction(
                id = System.currentTimeMillis().toString(),
                title = title,
                amount = amount,
                type = TransactionType.INCOME
            )
        )
    }

    fun addExpense(title: String, amount: Double) {
        repository.addTransaction(
            Transaction(
                id = System.currentTimeMillis().toString(),
                title = title,
                amount = amount,
                type = TransactionType.EXPENSE
            )
        )
    }

    fun totalBalance(): Double {
        return transactions.value.sumOf {
            if (it.type == TransactionType.INCOME) it.amount else -it.amount
        }
    }
}
