package com.example.budgettracking.viewmodel

import androidx.lifecycle.ViewModel
import com.example.budgettracking.data.model.Transaction
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.data.repository.FakeTransactionRepository
import com.example.budgettracking.data.repository.TransactionRepository
import kotlinx.coroutines.flow.map

class TransactionViewModel : ViewModel() {

    private val repository: TransactionRepository =
        FakeTransactionRepository()

    val transactions = repository.transactions

    // ---------- TOTAL INCOME ----------
    val totalIncome = transactions.map { list ->
        list
            .filter { it.type == TransactionType.INCOME }
            .sumOf { it.amount }
    }

    // ---------- TOTAL EXPENSE ----------
    val totalExpense = transactions.map { list ->
        list
            .filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amount }
    }

    // ---------- BALANCE ----------
    val balance = transactions.map { list ->
        list.sumOf {
            if (it.type == TransactionType.EXPENSE)
                -it.amount
            else
                it.amount
        }
    }

    fun addTransaction(transaction: Transaction) {
        repository.addTransaction(transaction)
    }
}